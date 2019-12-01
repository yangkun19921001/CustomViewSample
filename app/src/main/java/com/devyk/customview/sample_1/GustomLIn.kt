package com.devyk.customview.sample_1

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton

/**
 * <pre>
 *     author  : devyk on 2019-11-17 16:15
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is Button
 * </pre>
 */

public class GustomLIn(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    /**
     * 是否需要拦截父容器的点击事件方法
     */
    private var isIntercepted = false

    /**
     * 记录上一下的坐标
     */
    private var mLastXIntercept = 0f
    private var mLastYIntercept = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN)
            println("事件分发机制处理 ----> 父容器 LinearLayout onTouchEvent")
        return false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN)
            println("事件分发机制开始分发 ----> 父容器  dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                isIntercepted = false
                println("事件分发机制开始分发 ---->   onInterceptTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                //拦截子类的移动事件
                if (true) {
                    println("事件分发机制开始分发 ----> 拦截子类的移动事件  onInterceptTouchEvent")
                    isIntercepted = true
                } else {
                    isIntercepted = false
                }

            }
            MotionEvent.ACTION_UP -> {
                isIntercepted = false
                println("事件分发机制开始分发 ---->   onInterceptTouchEvent ACTION_UP")
            }
        }
        return isIntercepted
    }
}