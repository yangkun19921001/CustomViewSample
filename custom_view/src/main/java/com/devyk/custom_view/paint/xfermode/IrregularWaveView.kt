package com.devyk.custom_view.paint.xfermode

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-12-01 14:20
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is IrregularWaveView
 * </pre>
 */
class IrregularWaveView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mPaint: Paint
    private var mItemWaveLength = 0
    private var dx = 0

    private val BmpSRC: Bitmap
    private val BmpDST: Bitmap

    init {
        mPaint = Paint()

        BmpDST = BitmapFactory.decodeResource(resources, R.drawable.wav, null)
        BmpSRC = BitmapFactory.decodeResource(resources, R.drawable.circle_shape, null)
        //不要让它超出边界
        mItemWaveLength = BmpDST.width - BmpSRC.width

        startAnim()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //先画上圆形
        canvas.drawBitmap(BmpSRC, 0f, 0f, mPaint)

        //再画上结果
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.drawBitmap(
            BmpDST,
            Rect(dx, 0, dx + BmpSRC.width, BmpSRC.height),
            Rect(0, 0, BmpSRC.width, BmpSRC.height),
            mPaint
        )
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(BmpSRC, 0f, 0f, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(layerId)


    }


    fun startAnim() {
        val animator = ValueAnimator.ofInt(0, mItemWaveLength)
        animator.duration = 2000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            dx = animation.animatedValue as Int
            postInvalidate()
        }
        animator.start()
    }
}