package com.devyk.customview.sample_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * <pre>
 *     author  : devyk on 2019-11-16 18:01
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is SlideView1
 * </pre>
 */
public class SlideView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    /**
     * 记录上次滑动的坐标
     */
    private var mLastX = 0;
    private var mLastY = 0;

    /**
     * 初始化画笔
     */
    val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = 3f
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //拿到相对于屏幕按下的坐标点
                mLastX = event.getX().toInt();
                mLastY = event.getY().toInt();
                println("拿到相对于屏幕按下的坐标点: x:$mLastX y:$mLastY")

            }
            MotionEvent.ACTION_MOVE -> {
                var offsetX = event.getX().toInt() - mLastX;//计算 View 新的摆放位置
                var offsetY = event.getY().toInt() - mLastY;
                //重新放置新的位置
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
            }

            MotionEvent.ACTION_UP -> {

            }
        }
        return true//消耗触摸事件
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(300f, 300f, 150f, paint)
    }

}