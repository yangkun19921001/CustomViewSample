package com.devyk.customview.sample_1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.*
import android.widget.Scroller

/**
 * <pre>
 *     author  : devyk on 2019-11-14 00:22
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CustomView_1
 * </pre>
 */
class CustomView_1 : View {



    /**
     * 定义一个画笔
     */
    private var pint = Paint();

    private val mSollor = Scroller(context);

    /**
     * 获取最小滑动
     */
    private  var scaledDoubleTapSlop = 0

    /**
     * 速度追踪
     */
    private lateinit var  obtain :VelocityTracker

    /**
     * 手势
     */
    private lateinit var mGetDetector : GestureDetector

    /**
     * 平滑滑动
     */
    private lateinit var mScroller: Scroller


    constructor(context: Context?) : super(context) {
        init()
    }

    private fun init() {
        pint.strokeWidth = 10f
        pint.color = Color.RED
        pint.isAntiAlias = true
        pint.strokeCap = Paint.Cap.ROUND

        mScroller = Scroller(context)


        /**
         * 系统所能识别出来的被认为滑动的最小距离
         */
        scaledDoubleTapSlop = ViewConfiguration.get(context).scaledDoubleTapSlop;
        mGetDetector.setIsLongpressEnabled(false)

        /**
         *  手势识别
         */
        mGetDetector = GestureDetector(context,object :GestureDetector.OnGestureListener{
            override fun onShowPress(e: MotionEvent?) {
                println("onShowPress")
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                println("onSingleTapUp")
              return  true
            }

            override fun onDown(e: MotionEvent?): Boolean {
                println("onDown")
                return true
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                println("onFling")
                return true
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                println("onScroll")
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                println("onLongPress")
            }
        })
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(400f, 400f, 200f, pint)


        val x = x
        val y = y
        val translationX = translationX
        val translationY = translationY

        println("x:$x y:$y translationX:$translationX translationY:$translationY")


    }


    var  lastX = 0
    var lasty = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var rawX = event.getRawX()
        var rawY = event.getRawY()



        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                /**
                 * 速度追踪
                 */
                 obtain = VelocityTracker.obtain()
                obtain.addMovement(event)
                println("ACTION_DOWN getX:$scrollX getY:$y")
            }
            MotionEvent.ACTION_UP -> {

                var targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                var dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(-100, 0, dx - 100, 0);
                invalidate();

            }
            MotionEvent.ACTION_MOVE -> {
                obtain.computeCurrentVelocity(1000)
                val xVelocity = obtain.getXVelocity()
                val yVelocity = obtain.getYVelocity()
                val curY = -(rawY - lasty)
                val curX = -(rawX- lastX)

                println("curX:$curX curY:$curY")
                obtain.clear()
                scrollBy(-curX.toInt(),-curY.toInt())
            }
        }
        lastX = rawX.toInt()
        lasty = rawY.toInt()
        return true

    }




    override fun computeScroll() {
        super.computeScroll()
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(-100,-100);
            invalidate();
        }
    }






}