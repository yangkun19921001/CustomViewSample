package com.devyk.customview.sample_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * <pre>
 *     author  : devyk on 2019-11-16 14:22
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CircleView
 * </pre>
 */
class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mWidth = 0;
    var mHeight = 0;


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawCircle(400f,400f,200f, Paint().also {
            it.isAntiAlias = true
            it.strokeWidth = 5f
            it.strokeCap = Paint.Cap.ROUND
            it.color = Color.RED
        })
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        /**
         * 说明在布局中使用了 wrap_content 模式
         */
        if (widthMeasureSpec == MeasureSpec.AT_MOST && heightMeasureSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth,mHeight)
        }else if (widthMeasureSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth,heightSize)
        }else if (heightMeasureSpec == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,mHeight)
        }
    }
}