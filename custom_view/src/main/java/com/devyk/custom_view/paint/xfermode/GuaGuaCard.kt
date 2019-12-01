package com.devyk.custom_view.paint.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-12-01 15:05
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is GuaGuaCard
 * </pre>
 */
class GuaGuaCardView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mBitPaint: Paint
    private val BmpDST: Bitmap
    private val BmpSRC: Bitmap
    private val BmpText: Bitmap
    private val mPath: Path
    private var mPreX: Float = 0.toFloat()
    private var mPreY: Float = 0.toFloat()

    init {

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mBitPaint = Paint()
        mBitPaint.color = Color.RED
        mBitPaint.style = Paint.Style.STROKE
        mBitPaint.strokeWidth = 45f

        BmpText = BitmapFactory.decodeResource(resources, R.drawable.guaguaka_text1, null)
        BmpSRC = BitmapFactory.decodeResource(resources, R.drawable.guaguaka, null)
        BmpDST = Bitmap.createBitmap(BmpSRC.width, BmpSRC.height, Bitmap.Config.ARGB_8888)
        mPath = Path()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(BmpText, 0f, 0f, mBitPaint)

        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        //先把手指轨迹画到目标Bitmap上
        val c = Canvas(BmpDST)
        c.drawPath(mPath, mBitPaint)

        //然后把目标图像画到画布上
        canvas.drawBitmap(BmpDST, 0f, 0f, mBitPaint)

        //计算源图像区域
        mBitPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas.drawBitmap(BmpSRC, 0f, 0f, mBitPaint)

        mBitPaint.xfermode = null
        canvas.restoreToCount(layerId)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath.moveTo(event.x, event.y)
                mPreX = event.x
                mPreY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = (mPreX + event.x) / 2
                val endY = (mPreY + event.y) / 2
                mPath.quadTo(mPreX, mPreY, endX, endY)
                mPreX = event.x
                mPreY = event.y
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }
}