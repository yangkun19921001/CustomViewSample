package com.devyk.custom_view.paint.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.devyk.custom_view.BitmapUtis
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-12-01 13:15
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RoudImageView
 * </pre>
 */

public class RoudImageView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    var mBitPaint =  Paint()
    private lateinit var mBmpDST: Bitmap
    private lateinit var mBmpSRC: Bitmap
    private fun init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mBmpDST = BitmapUtis.changeBitmapSize(context,R.mipmap.gild_3)
        mBmpSRC =  BitmapUtis.changeBitmapSize(context,R.drawable.shade)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val saveLayer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        canvas.drawBitmap(mBmpDST,0f,0f,mBitPaint)
        mBitPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_IN))
        canvas.drawBitmap(mBmpSRC,0f,0f,mBitPaint)

        mBitPaint.setXfermode(null)
        canvas.restoreToCount(saveLayer)
    }

}