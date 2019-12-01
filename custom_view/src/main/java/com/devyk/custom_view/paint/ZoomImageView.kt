package com.devyk.custom_view.paint

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-11-30 19:56
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ZoomImageView
 * </pre>
 */
/*
 * 放大镜效果
 */

class ZoomImageView : View {
    // 原图
    private val mBitmap: Bitmap
    // 放大后的图
    private var mBitmapScale: Bitmap? = null
    // 制作的圆形的图片（放大的局部），盖在Canvas上面
    private val mShapeDrawable: ShapeDrawable

    private val mMatrix: Matrix

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.gild_3)
        mBitmapScale = mBitmap
        //放大后的整个图片
        mBitmapScale = Bitmap.createScaledBitmap(
            mBitmapScale!!, mBitmapScale!!.width * FACTOR,
            mBitmapScale!!.height * FACTOR, true
        )
        val bitmapShader = BitmapShader(
            mBitmapScale!!, Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )

        mShapeDrawable = ShapeDrawable(OvalShape())
        mShapeDrawable.paint.shader = bitmapShader
        // 切出矩形区域，用来画圆（内切圆）
        mShapeDrawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2)

        mMatrix = Matrix()
    }




    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        // 1、画原图
        canvas.drawBitmap(mBitmap, 0f, 0f, null)

        // 2、画放大镜的图
        mShapeDrawable.draw(canvas)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt() - RADIUS

        Log.d("onTouchEvent", "x:" + x + "y:" + y)

        // 将放大的图片往相反的方向挪动
        mMatrix.setTranslate((RADIUS - x * FACTOR).toFloat(), (RADIUS - y * FACTOR).toFloat())
        mShapeDrawable.paint.shader.setLocalMatrix(mMatrix)
        // 切出手势区域点位置的圆
        mShapeDrawable.setBounds(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS)
//        invalidate()
        postInvalidate()
        return true
    }

    companion object {

        //放大倍数
        private val FACTOR = 3
        //放大镜的半径
        private val RADIUS = 300
    }
}