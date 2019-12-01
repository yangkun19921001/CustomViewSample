package com.devyk.custom_view.paint.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-12-01 14:58
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is TwitterView
 * </pre>
 */
class TwitterView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mBitPaint: Paint
    private val BmpDST: Bitmap
    private val BmpSRC: Bitmap

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mBitPaint = Paint()
        //目标图像
        BmpDST = BitmapFactory.decodeResource(resources, R.drawable.twiter_bg, null)
        //原图像
        BmpSRC = BitmapFactory.decodeResource(resources, R.drawable.twiter_light, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        canvas.drawBitmap(BmpDST, 0f, 0f, mBitPaint)
        mBitPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
        canvas.drawBitmap(BmpSRC, 0f, 0f, mBitPaint)

        mBitPaint.xfermode = null
        canvas.restoreToCount(layerId)
    }
}