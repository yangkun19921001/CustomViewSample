package com.devyk.customview.sample_1

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Scroller

/**
 * <pre>
 *     author  : devyk on 2019-11-16 13:47
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ScrollerSample_1
 * </pre>
 */

class ScrollerSample_1 : LinearLayout {

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context) : super(context)

    /**
     * 定义滑动 Scroller
     */
    private val mScroller = Scroller(context)


    public fun smoothScrollTo(destX: Int = -100, destY: Int = -100) {
        //滑动了的位置
        val scrollX = scrollY;
        val delta = destY - scrollY;
        //2000 ms 内滑动到 destX 位置，效果就是缓慢滑动
        mScroller.startScroll(scrollX, 0, 0, delta, 2000)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
        }
    }
}