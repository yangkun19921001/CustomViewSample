package com.devyk.custom_view.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView

/**
 * <pre>
 *     author  : devyk on 2019-11-30 17:35
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is LinearGradientTextView
 * </pre>
 */
class LinearGradientTextView : TextView {

    /**
     * 定义线性渐变
     */
    private var mLinearGradient: LinearGradient? = null

    /**
     * 定义一个矩阵
     */
    private var mGradientatrix: Matrix? = null

    /**
     * 定义一个画笔
     */
    private var mPaint: Paint? = null

    private var mViewWidth = 0
    private var mTranslate = 0

    private var delta = 15



    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    /**
     * 当字改变的时候回调
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mViewWidth ==0){
            //拿到当前 text 宽度
            mViewWidth = measuredWidth
            if (mViewWidth > 0){
                //拿到当前画笔
                mPaint = paint
                //拿到 text
                var text = text.toString()
                //mViewWidth除字体总数就得到了每个字的像素    然后*3 表示3个文字的像素
                var size = 0;
                //如果当前 text 长度大于 0
                if (text.length > 0){
                    //拿到当前 3 个文字的像素
                    size = mViewWidth / text.length * 3

                }else{//说明没有文字
                    size = mViewWidth
                }
                /**线性渲染
                 * x0, y0, 起始点
                 *  x1, y1, 结束点
                 * int[]  mColors, 中间依次要出现的几个颜色
                 * float[] positions 位置数组，position的取值范围[0,1]，作用是指定几个颜色分别放置在那个位置上，
                 * 如果传null，渐变就线性变化。
                 *    tile 用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
                 */
                //从左边 size 开始，左边看不见的地方开始，以滚动扫描的形式过来
                mLinearGradient = LinearGradient(-size.toFloat(),0f,0f,0f, intArrayOf(0x33ffffff, -0x1, 0x33ffffff),
                    floatArrayOf(0f, 0.2f, 1f), Shader.TileMode.CLAMP)
                //将线性渐变添加到 paint 中
                mPaint!!.setShader(mLinearGradient)
                //定义一个矩阵
                mGradientatrix = Matrix()
            }
        }
    }

    /**
     * 开始绘制
     */
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val measureWindth = paint.measureText(text.toString())
        mTranslate += delta
        /**
         * 如果位置已经移动到了边界，那么文字就开始往回滚动
         * 但是如果小于 1 那么又开始递增，执行另一个逻辑
         */
        if (mTranslate > measureWindth + 1 || mTranslate < 1){
            delta = -delta
        }

        //将矩阵平移
        mGradientatrix!!.setTranslate(mTranslate.toFloat(),0f)
        mLinearGradient!!.setLocalMatrix(mGradientatrix)
        //paint是textview的所以只需要不断色控制画笔的shader  然后利用矩阵控制位移即可
        postInvalidateDelayed(30)

    }
}