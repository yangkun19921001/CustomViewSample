package com.devyk.customview.sample_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.devyk.customview.R


/**
 * <pre>
 *     author  : devyk on 2019-11-27 16:32
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CircleView3
 * </pre>
 */
class CircleView3 : View {


    var color = Color.RED

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context) {
        init()
    }



    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initTypedrray(context,attrs)
        init()
    }

    private fun initTypedrray(context: Context, attrs: AttributeSet) {
        //拿到自定义属性组
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CircleView)
        color = obtainStyledAttributes.getColor(R.styleable.CircleView_circle_view_color, Color.RED)
        obtainStyledAttributes.recycle()

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    /**
     * 解决 wrap_content
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200)
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSpecSize)
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 200)
        }
    }


    /**
     * 解决 padding
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom
        val paddingTop = paddingTop
        val height = height - paddingBottom - paddingTop
        val width = width - paddingLeft - paddingRight
        val radius = Math.min(width, height) / 2f

        canvas.drawCircle(paddingLeft + width/2f,paddingTop + height/2f,radius,paint)


    }


    private fun init() {
        paint.setColor(color)
        paint.isAntiAlias = true
    }
}