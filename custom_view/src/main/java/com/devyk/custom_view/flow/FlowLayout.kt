package com.devyk.custom_view.flow

import android.content.Context
import android.util.AttributeSet
import android.util.LayoutDirection
import android.util.Log
import android.view.ViewGroup
import android.view.View
import androidx.core.text.TextUtilsCompat
import java.util.*
import kotlin.collections.ArrayList
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.devyk.custom_view.R


/**
 * <pre>
 *     author  : devyk on 2019-11-27 23:14
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is FlowLayout 自定义 ViewGroup 实现流式布局
 * </pre>
 */
open class FlowLayout : ViewGroup {

    /**
     * 打 log TAG
     */
    private val TAG = this.javaClass.simpleName

    /**
     * 定义一个装所有子 View 的容器
     */
    protected var mAllViews: MutableList<List<View>> = ArrayList<List<View>>()
    /**
     * 定义行高
     */
    protected var mLineHeight: MutableList<Int> = ArrayList()
    /**
     * 定义行宽
     */
    protected var mLineWidth: MutableList<Int> = ArrayList()
    /**
     * 当前行上的子 View 控件
     */
    protected var mLinViews: MutableList<View> = ArrayList<View>()

    /**
     * 拿到布局的方法
     */
    private var mGravity = -1;

    /**
     * 布局摆放位置，左/中/右 开始
     */
    private val LEFT = -1
    private val CENTER = 0
    private val RIGHT = 1


    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }


    /**
     * 初始化自定义属性
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        //拿到定义的属性组
        val osa = context.obtainStyledAttributes(R.styleable.FlowLayout)
        //拿到布局的摆放方向
        mGravity = osa.getInt(R.styleable.FlowLayout_gravity, LEFT)
        //返回当前布局方向[API 使用文档](https://developer.android.com/reference/androidx/core/text/TextUtilsCompat)
        val layoutDirectionFromLocale = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
        if (layoutDirectionFromLocale == LayoutDirection.RTL) {
            when (mGravity) {
                LEFT -> mGravity = RIGHT
                else -> mGravity = LEFT
            }
        }
        osa.recycle()
    }
    /**
     * 1. 确定所有子 View 的宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //根据当前宽高的测量模式，拿到宽高和当前模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightsize = MeasureSpec.getSize(heightMeasureSpec)

        //如果当前容器 XML 布局定义的 wrap_content 那么就需要自己解决实际测量高度
        var width = 0
        var height = 0

        //当前行高/宽
        var lineWidth = 0
        var lineHeight = 0

        //拿到所有子 View 总数
        val allViewCount = childCount

        //遍历进行对子 View 进行测量
        for (child in 0..allViewCount -1 ){
            //拿到当前 View
            var childView = getChildAt(child)
            //判断当前 view 是否隐藏状态
            if (childView.visibility == View.GONE) {
                //如果是最后一个,拿到当前行高
                if (child == allViewCount - 1){
                    width = Math.max(lineWidth,width)
                    height += lineHeight
                }
                continue
            }
            //对 childView 进行测量
            measureChild(childView,widthMeasureSpec,heightMeasureSpec)
            //拿到当前子 View 布局参数
            val marginLayoutParams = childView.layoutParams as MarginLayoutParams
            //拿到测量之后的宽、高 + 设置的 margin
            val childWidth = childView.measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin
            val childHeight = childView.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin

            //说明已经放不下
            if (lineWidth + childWidth > widthSize - paddingLeft - paddingRight){
                //拿到当前行最大的宽值
                width = Math.max(width,lineWidth)
                //当前行的宽度
                lineWidth = childWidth
                //总高读
                height += lineHeight
                //当前行的高度
                lineHeight = childHeight
            }else{
                //将子 View 的宽度累计相加
                lineWidth += childWidth
                //拿到当前行大的高度
                lineHeight = Math.max(lineHeight,childHeight)

            }
        }

Log.d(TAG,"width:${ if (widthMode === MeasureSpec.EXACTLY) widthSize else width + paddingLeft + paddingRight}   heigth:${if (heightMode === MeasureSpec.EXACTLY) heightsize else height + paddingTop + paddingBottom}");
        //设置当前容器的宽高
        setMeasuredDimension(
            //判断是否是 match——parent 模式
            if (widthMode === MeasureSpec.EXACTLY) widthSize else width + paddingLeft + paddingRight,
            if (heightMode === MeasureSpec.EXACTLY) heightsize else height + paddingTop + paddingBottom
        )
    }


    /**
     * 2. 确定所有子 View 的位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //清空容器里面的数据
        mAllViews.clear()
        mLineHeight.clear()
        mLineWidth.clear()
        mLinViews.clear()

        //拿到控件的宽
        var width  = width
        //当前行宽
        var lineWidth = 0
        //当前行高
        var lineHeight = 0
        //当前 childCount
        val childCount = childCount
        //遍历子 View
        for (childIndex in 0..childCount-1){
            var childView  = getChildAt(childIndex)
            if(childView.visibility == View.GONE)continue
            val marginLayoutParams = childView.layoutParams as MarginLayoutParams
            //拿到最后 View 真实宽高
            val measuredWidth = childView.measuredWidth
            val measuredHeight = childView.measuredHeight

            //当前子 View 的宽+ 当前行宽再加当前 margin 如果大于当前总宽的话 说明放不下了，需要换行
            if (measuredWidth + lineWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin > width - paddingRight-paddingLeft){
                //当前行的最大的高
                mLineHeight.add(lineHeight)
                //当前行总宽度
                mLineWidth.add(lineWidth)
                //这里面装的是每一行所有的子View，该容器的 size 取决于 有多少行
                mAllViews.add(mLinViews)
                //将下一行的宽设置为 0 初始高度
                lineWidth = 0
                //将下一行的高度初始为第一个子 View 的高度
                lineHeight = measuredHeight
                //初始化一个容器，用于装下一行所有的子 View
                mLinViews = ArrayList<View>()

                Log.d(TAG,"lineWidth:$lineWidth lineHeight:$lineHeight")
            }

            //依次加当前 View 占用的宽
            lineWidth += measuredWidth
            //找出当前子 View 最大的height
            lineHeight = Math.max(lineHeight,measuredHeight + marginLayoutParams.bottomMargin + marginLayoutParams.topMargin)
            //将行上的 VIew 添加到容器里面
            mLinViews.add(childView)
            Log.d(TAG,"--- lineWidth:$lineWidth lineHeight:$lineHeight")
        }


        mLineHeight.add(lineHeight)
        mLineWidth.add(lineWidth)
        mAllViews.add(mLinViews)

        var left = paddingLeft
        var top  = paddingTop

        //拿到当前所有的子 VIew
        for (curAllView in 0..mAllViews.size -1){
            mLinViews = mAllViews.get(curAllView) as ArrayList
            lineHeight = mLineHeight.get(curAllView)

            val curLinewidth = mLineHeight.get(curAllView)
            when(mGravity){
                LEFT -> left = paddingLeft
                CENTER -> (width - curLinewidth)/2 + paddingLeft
                RIGHT -> {
                    left = width - (curLinewidth + paddingLeft) - paddingRight
                    Collections.reverse(mLinViews)
                }
            }

            mLinViews.forEach lit@{
                if (it.visibility == View.GONE)return@lit

                val lp = it.layoutParams as MarginLayoutParams
                var lc = left + lp.leftMargin
                var tc = top + lp.topMargin
                var rc = lc + it.measuredWidth
                var bc = tc + it.measuredHeight

                Log.d(TAG,"lc:$lc tc:$tc rc:$rc bc:$bc");

                //开始放入子 VIew
                it.layout(lc,tc,rc,bc)

                left += it.measuredWidth + lp.leftMargin + lp.rightMargin
            }
            top += lineHeight
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}


