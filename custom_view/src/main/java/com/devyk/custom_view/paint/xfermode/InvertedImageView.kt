package com.devyk.custom_view.paint.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.devyk.custom_view.BitmapUtis
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-12-01 14:03
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is InvertedImageView
 * </pre>
 */

class InvertedImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mBitPaint: Paint
    private val BmpDST: Bitmap
    private val BmpSRC: Bitmap
    private val BmpRevert: Bitmap

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mBitPaint = Paint()
        BmpDST = BitmapUtis.changeBitmapSize(context,R.mipmap.gild_3)
        BmpSRC = BitmapUtis.changeBitmapSize(context,R.drawable.invert_shade)

        val matrix = Matrix()
        matrix.setScale(1f, -1f)
        // 生成倒影图
        BmpRevert = Bitmap.createBitmap(BmpDST, 0, 0, BmpDST.width, BmpDST.height, matrix, true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.BLACK)


        //先画出原始图片
        canvas.drawBitmap(BmpDST, 0f, 0f, mBitPaint)

        //再画出倒影
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.translate(0f, BmpSRC.height.toFloat())

        canvas.drawBitmap(BmpRevert, 0f, 0f, mBitPaint)
        mBitPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(BmpSRC, 0f, 0f, mBitPaint)

        mBitPaint.xfermode = null

        canvas.restoreToCount(layerId)
    }
}