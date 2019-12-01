package com.devyk.customview.sample_1

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     author  : devyk on 2019-11-20 00:08
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is HorizontalScrollView
 * </pre>
 */
class MyRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    /**
     * 分别记录我们上次滑动的坐标
     */
    private var mLastX = 0;
    private var mLastY = 0;

    constructor(context: Context) : this(context, null)


    /**
     * 重写分发事件
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.getX().toInt()
        val y = ev.getY().toInt()

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
            var par =    parent as ScrollerViewPager
                //请求父类不要拦截事件
                par.requestDisallowInterceptTouchEvent(true)
                Log.d("dispatchTouchEvent", "---》子ACTION_DOWN");
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                val deltaY = y - mLastY

                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    var par =    parent as ScrollerViewPager
                    Log.d("dispatchTouchEvent", "dx:" + deltaX + " dy:" + deltaY);
                    //交于父类来处理
                    par.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }


        mLastX = x
        mLastY = y
        return super.dispatchTouchEvent(ev)
    }

}