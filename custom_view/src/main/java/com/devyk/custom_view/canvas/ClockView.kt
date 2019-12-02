package com.devyk.custom_view.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint.Style.*
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.*


/**
 * <pre>
 *     author  : devyk on 2019-12-02 20:18
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ClockView
 * </pre>
 */
class ClockView : View {

    //外圆画笔
    private var mPaint: Paint? = null
    //文字画笔
    private var mPaintNum: Paint? = null
    //时钟画笔
    private var mPaintHour: Paint? = null
    //分钟画笔
    private var mPaintMinute: Paint? = null
    //秒钟画笔
    private var mPaintSecond: Paint? = null
    //外圆圆心
    private var mX = 0f
    private var mY = 0f
    //外圆半径
    private var mR: Int = 0

    private val start = "12"

    constructor(context: Context) : super(context) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
    }


    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.color = Color.BLACK
        mPaint!!.isAntiAlias = true
        mPaint!!.strokeWidth = 3f
        mPaint!!.style = STROKE

        mPaintNum = Paint()
        mPaintNum!!.color = Color.BLACK
        mPaintNum!!.isAntiAlias = true
        mPaintNum!!.textSize = 35f
        mPaintNum!!.style = STROKE
        mPaintNum!!.textAlign = Paint.Align.CENTER

        mPaintSecond = Paint()
        mPaintSecond!!.color = Color.RED
        mPaintSecond!!.isAntiAlias = true
        mPaintSecond!!.strokeWidth = 5f
        mPaintSecond!!.style = FILL

        mPaintMinute = Paint()
        mPaintMinute!!.color = Color.BLACK
        mPaintMinute!!.isAntiAlias = true
        mPaintMinute!!.strokeWidth = 8f
        mPaintMinute!!.style = FILL

        mPaintHour = Paint()
        mPaintHour!!.color = Color.BLACK
        mPaintHour!!.isAntiAlias = true
        mPaintHour!!.strokeWidth = 13f
        mPaintHour!!.style = FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = measuredHeight
        val width = measuredWidth
        mX = (width / 2).toFloat()
        mY = (height / 2).toFloat()
        mR = mX.toInt() - 5
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //1. 绘制外圆
        canvas.drawCircle(mX, mY, mR.toFloat(), mPaint!!)

        //2. 绘制圆心
        canvas.drawCircle(mX, mY, 15f, mPaintMinute!!)

        //3. 绘制刻度
        drawLines(canvas)

        //4. 绘制整点
        drawText(canvas)

        //5. 更新时间
        updateCurrentTime(canvas)
    }


/*    *
     * 绘制时钟刻度和分钟刻度
     *
     * @param canvas 画布*/

    private fun drawLines(canvas: Canvas) {
        for (i in 0..59) {
            if (i % 5 == 0) {
                //绘制整点刻度
                mPaint!!.strokeWidth = 8f
                canvas.drawLine(mX, mY - mR, mX, mY - mR + 40, mPaint!!)
            } else {
                mPaint!!.strokeWidth = 3f
                //绘制分钟刻度
                canvas.drawLine(mX, mY - mR, mX, mY - mR + 30, mPaint!!)
            }
            //绕着(x,y)旋转6°
            canvas.rotate(6f, mX, mY)
        }
    }

/*    *
     * 绘制整点数字
     *
     * @param canvas 画布*/

    private fun drawText(canvas: Canvas) {
        // 获取文字高度用于设置文本垂直居中
        val textSize = mPaintNum!!.fontMetrics.bottom - mPaintNum!!.fontMetrics.top
        // 数字离圆心的距离,40为刻度的长度,20文字大小
        val distance = mR - 40 - 20
        // 数字的坐标(a,b)
        var a: Float
        var b: Float
        mPaintNum!!.isAntiAlias = true
        mPaintNum!!.textSize = 50f
        mPaintNum!!.strokeWidth = 5f
        mPaintNum!!.style = STROKE
        // 每30°写一个数字
        for (i in 0..11) {
            a = (distance * Math.sin(i.toDouble() * 30.0 * Math.PI / 180) + mX).toFloat()
            b = (mY - distance * Math.cos(i.toDouble() * 30.0 * Math.PI / 180)).toFloat()

            if (i == 0) {
                canvas.drawText(start, a, b + textSize / 3, mPaintNum!!)
            } else {
                canvas.drawText(i.toString(), a, b + textSize / 3, mPaintNum!!)
            }
        }
    }

/*    *
     * 获取当前系统时间
     *
     * @param canvas 画布*/

    private fun updateCurrentTime(canvas: Canvas) {
        //获取系统当前时间
        val format = SimpleDateFormat("HH-mm-ss")
        val time = format.format(Date(System.currentTimeMillis()))
        val split = time.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val hour = Integer.parseInt(split[0])
        val minute = Integer.parseInt(split[1])
        val second = Integer.parseInt(split[2])
        //时针走过的角度
        val hourAngle = hour * 30 + minute / 2
        //分针走过的角度
        val minuteAngle = minute * 6 + second / 10
        //秒针走过的角度
        val secondAngle = second * 6

        //绘制时钟,以12整点为0°参照点
        drawLine(canvas, hourAngle, 170, mPaintHour)

        //绘制分钟
        drawLine(canvas, minuteAngle, 60, mPaintMinute)

        //绘制秒钟
        canvas.rotate(secondAngle.toFloat(), mX, mY)
        canvas.drawLine(mX, mY, mX, mY - mR + 80, mPaintSecond!!)

        //每隔1s刷新界面
        postInvalidateDelayed(1000)
    }


    fun drawLine(canvas: Canvas, angle: Int, length: Int, mPaint: Paint?) {
        canvas.rotate(angle.toFloat(), mX, mY)
        canvas.drawLine(mX, mY, mX, mY - mR + length, mPaint!!)
        canvas.save()
        canvas.restore()
        //这里画好了时钟，我们需要再将画布转回来,继续以12整点为0°参照点
        canvas.rotate((-angle).toFloat(), mX, mY)
    }

}
