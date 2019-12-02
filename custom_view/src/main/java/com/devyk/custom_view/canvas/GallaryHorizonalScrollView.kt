package com.devyk.custom_view.canvas

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout

/**
 * <pre>
 *     author  : devyk on 2019-12-02 18:23
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is GallaryHorizonalScrollView
 * </pre>
 */

class GallaryHorizonalScrollView : HorizontalScrollView, View.OnTouchListener {
    private var container: LinearLayout? = null
    private var centerX: Int = 0
    private var icon_width: Int = 0

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        //在ScrollView里面放置一个水平线性布局，再往里面放置很多ImageView
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        container = LinearLayout(context)
        container!!.layoutParams = params
        setOnTouchListener(this)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //得到某一张图片的宽度
        val v = container!!.getChildAt(0)
        icon_width = v.width
        Log.d(TAG, "icon_width = $icon_width")
        //得到hzv的中间x坐标
        centerX = width / 2
        Log.d(TAG, "centerX = $centerX")
        //处理下，中心坐标改为中心图片的左边界
        centerX = centerX - icon_width / 2
        //给LinearLayout和hzv之间设置边框距离
        container!!.setPadding(centerX, 0, centerX, 0)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        Log.e(TAG, "onScrollChanged")
        //渐变图片
        reveal()
    }

    private fun reveal() {
        // 渐变效果
        //得到hzv滑出去的距离
        val scrollX = scrollX
        Log.d(TAG, scrollX.toString() + "")
        //找到两张渐变的图片的下标--左，右
        val index_left = scrollX / icon_width
        Log.d(TAG, index_left.toString() + "index_left")
        val index_right = index_left + 1
        //设置图片的level
        for (i in 0 until container!!.childCount) {
            if (i == index_left || i == index_right) {
                //变化
                //比例：

                val ratio = 5000f / icon_width
                val iv_left = container!!.getChildAt(index_left) as ImageView
                //scrollX%icon_width:代表滑出去的距离
                //滑出去了icon_width/2  icon_width/2%icon_width
                iv_left.setImageLevel(
                    //代表的是，我滑动之后的距离在5000份当中的份额
                    (5000 - scrollX % icon_width * ratio).toInt()
                )
                //右边
                if (index_right < container!!.childCount) {
                    val iv_right = container!!.getChildAt(index_right) as ImageView
                    //scrollX%icon_width:代表滑出去的距离
                    //滑出去了icon_width/2  icon_width/2%icon_width
                    iv_right.setImageLevel(
                        (10000 - scrollX % icon_width * ratio).toInt()
                    )
                }
            } else {
                //灰色
                val iv = container!!.getChildAt(i) as ImageView
                iv.setImageLevel(0)
            }
        }
    }

    //添加图片的方法
    fun addImageViews(revealDrawables: Array<Drawable?>) {
        for (i in revealDrawables.indices) {
            val img = ImageView(context)
            img.setImageDrawable(revealDrawables[i])
            container!!.addView(img)
            if (i == 0) {
                img.setImageLevel(5000)
            }
        }
        addView(container)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return false
    }

    companion object {
        private val TAG = this.javaClass.simpleName
    }
}