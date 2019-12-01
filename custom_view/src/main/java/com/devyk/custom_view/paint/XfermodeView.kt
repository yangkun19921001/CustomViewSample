package com.devyk.custom_view.paint

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * <pre>
 *     author  : devyk on 2019-11-30 22:06
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is XfermodeView
 * </pre>
 */
class XfermodeView : View {

    lateinit var mPaint: Paint
    internal var mItemSize = 0f
    internal var mItemHorizontalOffset = 0f
    internal var mItemVerticalOffset = 0f
    internal var mCircleRadius = 0f
    internal var mRectSize = 0f
    internal var mCircleColor = -0x33bc//黄色
    internal var mRectColor = -0x995501//蓝色
    internal var mTextSize = 25f

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.textSize = mTextSize
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.strokeWidth = 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //设置背景色
        //        canvas.drawARGB(255, 139, 197, 186);

        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        for (row in 0..3) {
            for (column in 0..3) {
                canvas.save()
                val layer =
                    canvas.saveLayer(0f, 0f, canvasWidth.toFloat(), canvasHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)
                mPaint.xfermode = null
                val index = row * 4 + column
                val translateX = (mItemSize + mItemHorizontalOffset) * column
                val translateY = (mItemSize + mItemVerticalOffset) * row
                canvas.translate(translateX, translateY)
                //画文字
                val text = sLabels[index]
                mPaint.color = Color.BLACK
                val textXOffset = mItemSize / 2
                val textYOffset = mTextSize + (mItemVerticalOffset - mTextSize) / 2
                canvas.drawText(text, textXOffset, textYOffset, mPaint)
                canvas.translate(0f, mItemVerticalOffset)
                //画边框
                mPaint.style = Paint.Style.STROKE
                mPaint.color = -0x1000000
                canvas.drawRect(2f, 2f, mItemSize - 2, mItemSize - 2, mPaint)
                mPaint.style = Paint.Style.FILL
                //画圆
                mPaint.color = mCircleColor
                val left = mCircleRadius + 3
                val top = mCircleRadius + 3
                canvas.drawCircle(left, top, mCircleRadius, mPaint)
                mPaint.xfermode = sModes[index]
                //画矩形
                mPaint.color = mRectColor
                val rectRight = mCircleRadius + mRectSize
                val rectBottom = mCircleRadius + mRectSize
                canvas.drawRect(left, top, rectRight, rectBottom, mPaint)
                mPaint.xfermode = null
                //canvas.restore();
                canvas.restoreToCount(layer)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mItemSize = w / 4.5f
        mItemHorizontalOffset = mItemSize / 6
        mItemVerticalOffset = mItemSize * 0.426f
        mCircleRadius = mItemSize / 3
        mRectSize = mItemSize * 0.6f
    }

    companion object {

        private val sModes = arrayOf<Xfermode>(
            PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            PorterDuffXfermode(PorterDuff.Mode.SRC),
            PorterDuffXfermode(
                PorterDuff.Mode.DST
            ),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            PorterDuffXfermode(
                PorterDuff.Mode.SRC_IN
            ),
            PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            PorterDuffXfermode(
                PorterDuff.Mode.DST_OUT
            ),
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            PorterDuffXfermode(
                PorterDuff.Mode.XOR
            ),
            PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            PorterDuffXfermode(
                PorterDuff.Mode.MULTIPLY
            ),
            PorterDuffXfermode(PorterDuff.Mode.SCREEN)
        )

        private val sLabels = arrayOf(
            "Clear",
            "Src",
            "Dst",
            "SrcOver",
            "DstOver",
            "SrcIn",
            "DstIn",
            "SrcOut",
            "DstOut",
            "SrcATop",
            "DstATop",
            "Xor",
            "Darken",
            "Lighten",
            "Multiply",
            "Screen"
        )
    }
}