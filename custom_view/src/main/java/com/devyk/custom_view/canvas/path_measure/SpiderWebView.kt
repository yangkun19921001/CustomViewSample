package com.devyk.custom_view.canvas.path_measure

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import com.devyk.custom_view.base.BaseView


/**
 * <pre>
 *     author  : devyk on 2019-12-04 16:04
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is SpiderWebView
 * </pre>
 */
class SpiderWebView : BaseView {
    private var count = 6
    private val angle = (Math.PI * 2 / count).toFloat()
    private var radius: Float = 0.toFloat()  //外接圆半径
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var titles = arrayOf("Java", "Android", "NDK", "C++", "C", "音视频编解码")
    private var data = doubleArrayOf(1.0, 0.30, 0.6, 0.5, 0.8, 0.2)


    private var netColor = Color.GRAY

    private var overlayColor = Color.BLUE
    private var textColor = Color.BLACK
    private var overlayalpha = 100
    private var tagsize = 50f


    private lateinit var netPaint: Paint
    private lateinit var valuePaint: Paint
    private lateinit var textPaint: Paint

    lateinit var mPathMeasure: PathMeasure

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var curValues = 0f


    init {
        init(context);
    }


    fun init(context: Context) {
        count = Math.min(data.size, titles.size)

        netPaint = Paint()
        netPaint.setAntiAlias(true)
        netPaint.setColor(netColor)
        netPaint.setStyle(Paint.Style.STROKE)

        valuePaint = Paint()
        valuePaint.setAntiAlias(true)
        valuePaint.setColor(overlayColor)
        valuePaint.setStyle(Paint.Style.FILL)

        textPaint = Paint()
        textPaint.setAntiAlias(true)
        textPaint.setTextSize(tagsize)
        textPaint.setColor(textColor)
        textPaint.setStyle(Paint.Style.FILL)

        mPathMeasure = PathMeasure()

/*
        var valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.repeatCount = 7
        valueAnimator.setDuration(200)
        valueAnimator.addUpdateListener {
            curValues = it.getAnimatedValue() as Float

            postInvalidate()
        }*/
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = Math.min(mViewWidth, mViewHeight) / 2 * 0.7f
        centerX = mViewWidth / 2f
        centerY = mViewHeight / 2f

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heighSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heighSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        //解决外部 wrap_content 模式
        if (widthSpecMode == MeasureSpec.AT_MOST && heighSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heighSpecSize)
        } else if (heighSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 200)

        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawNet(canvas!!)
        drawText(canvas!!)
        drawRegion(canvas!!)
    }

    private fun drawNet(canvas: Canvas) {
        //绘制六边形
        val path = mTempPath
        val r = radius / (count - 1) //每次递增的高度
        println("每个区域平分角度：$r")
        for (i in 0 until count) {
            //当前需要在哪个角度开始绘制
            val currentRadius = r * i
            println("当前区域角度：$currentRadius")
            path.reset()
            for (j in 0 until count) {
                if (j == 0) {
                    path.moveTo(centerX + currentRadius, centerY)
                } else {
                    if (j == 1){
                    val x = (centerX + currentRadius * Math.cos((angle * j).toDouble())).toFloat()
                    val y = (centerY + currentRadius * Math.sin((angle * j).toDouble())).toFloat()
                    println("绘制线坐标点：$x , $y 角度cas${angle * j} 角度sin${Math.sin((angle * j).toDouble())}")
                    path.lineTo(x, y)}
                }
            }

            path.close()
            canvas.drawPath(path, netPaint)
        }


        //绘制轴线
        for (i in 1 until count + 1) {
            path.moveTo(centerX, centerY)
            val x = (centerX + radius * Math.cos((angle * i).toDouble())).toFloat()
            val y = (centerY + radius * Math.sin((angle * i).toDouble())).toFloat()
            path.lineTo(x, y)
            canvas.drawPath(path, netPaint)

        }

    }

    private fun drawText(canvas: Canvas) {
        val fontMetrics = textPaint.getFontMetrics()
        val fontHeight = fontMetrics.descent - fontMetrics.ascent //文字的高度

        //修正标题
        for (i in 0 until count) {
            val x = (centerX + (radius + fontHeight / 2) * Math.cos((angle * i).toDouble())).toFloat()
            val y = (centerY + (radius + fontHeight / 2) * Math.sin((angle * i).toDouble())).toFloat()
            val dis = textPaint.measureText(titles[i])//获取文本长度

            if (angle * i > 0 && angle * i < Math.PI) {
                canvas.drawText(titles[i], x - dis / 2, y + fontHeight, textPaint)
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {
                canvas.drawText(titles[i], x - dis, y, textPaint)
            } else {
                canvas.drawText(titles[i], x, y, textPaint)
            }

        }

    }


    private fun drawRegion(canvas: Canvas) {
        mTempPath.rewind()
        val path = mTempPath
        for (i in 0 until count) {
            val x = (centerX + radius.toDouble() * Math.cos((angle * i).toDouble()) * data[i]).toFloat()
            val y = (centerY + radius.toDouble() * Math.sin((angle * i).toDouble()) * data[i]).toFloat()
            if (i == 0) {
                path.moveTo(x, centerY)
            } else {
                path.lineTo(x, y)
            }
            canvas.drawCircle(x, y, 5f, valuePaint)
        }
        path.close()
        valuePaint.setAlpha(overlayalpha)
        canvas.drawPath(path, valuePaint)


    }
}