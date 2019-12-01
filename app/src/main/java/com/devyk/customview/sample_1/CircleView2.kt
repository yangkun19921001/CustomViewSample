package com.devyk.customview.sample_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * <pre>
 *     author  : devyk on 2019-11-27 15:02
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CircleView2
 * </pre>
 */
class CircleView2: View {

    val color = Color.RED

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context) {
        init()
    }



    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val height = height
        val width = width
        val radius = Math.min(width, height) / 2f

        canvas.drawCircle(width/2f,height/2f,radius,paint)


    }


    private fun init() {
        paint.setColor(color)
        paint.isAntiAlias = true
    }
}

