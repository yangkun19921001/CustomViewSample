package com.devyk.customview.sample_1

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
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

public class Button(context: Context?, attrs: AttributeSet?) : AppCompatButton(context, attrs) {


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                println("事件分发机制开始分发 ----> 子View  dispatchTouchEvent ACTION_DOWN")
                parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_MOVE -> {
                println("事件分发机制开始分发 ----> 子View  dispatchTouchEvent ACTION_MOVE")
                if (true){
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
                println("事件分发机制开始分发 ----> 子View  dispatchTouchEvent ACTION_UP")
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var isHandler = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                println("事件分发机制处理 ----> 子View  onTouchEvent ACTION_DOWN")
                isHandler = true
            }
            MotionEvent.ACTION_MOVE -> {
                println("事件分发机制处理 ----> 子View  onTouchEvent ACTION_MOVE")
                isHandler = false
            }
            MotionEvent.ACTION_UP -> {
                println("事件分发机制处理 ----> 子View  onTouchEvent ACTION_UP")
                isHandler = true
            }
        }
        return isHandler
    }

}