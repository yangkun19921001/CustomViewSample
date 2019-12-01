package com.devyk.custom_view.paint.xfermode

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-12-01 14:37
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is HeartView
 * </pre>
 */
class HeartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mPaint: Paint
    private var mItemWaveLength = 0
    private var dx = 0

    private val BmpSRC: Bitmap
    private val BmpDST: Bitmap

    init {

        mPaint = Paint()
        mPaint.color = Color.RED

        BmpDST = BitmapFactory.decodeResource(resources, R.drawable.heartmap, null)
        BmpSRC = Bitmap.createBitmap(BmpDST.width, BmpDST.height, Bitmap.Config.ARGB_8888)

        mItemWaveLength = BmpDST.width

        startAnim()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val c = Canvas(BmpSRC)
        //清空bitmap
        c.drawColor(Color.RED, PorterDuff.Mode.CLEAR)
        Log.d("onDraw","左移动:${BmpDST.width - dx}");

        //画上矩形
        c.drawRect((BmpDST.width - dx).toFloat(), 0f, BmpDST.width.toFloat(), BmpDST.height.toFloat(), mPaint)

        //模式合成
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.drawBitmap(BmpDST, 0f, 0f, mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(BmpSRC, 0f, 0f, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(layerId)
    }


    fun startAnim() {
        val animator = ValueAnimator.ofInt(0, mItemWaveLength)
        animator.duration = 6000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            dx = animation.animatedValue as Int
            postInvalidate()
        }
        animator.start()
    }
}