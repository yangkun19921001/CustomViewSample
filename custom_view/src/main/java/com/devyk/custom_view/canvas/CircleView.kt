package com.devyk.custom_view.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.devyk.custom_view.base.BaseView

/**
 * <pre>
 *     author  : devyk on 2019-12-01 20:18
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CircleView
 * </pre>
 */
class CircleView(context: Context?) : BaseView(context) {






    /**
     * 初始化
     */
    override fun init(context: Context?, attrs: AttributeSet?) {
        super.init(context, attrs)


        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.setColor(Color.RED)
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = 5f



    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        //绘制一个圆
        canvas.drawCircle(500f, 500f, 150f, mPaint)
        mPaint.setColor(Color.WHITE)
        mPaint.strokeWidth = 10f
        //绘制一个点
        canvas.drawPoint(500f, 500f, mPaint)
        mPaint.setColor(Color.BLACK)
        //绘制一条线
        canvas.drawLine(500f,500f,650f,500f,mPaint)



    }
}