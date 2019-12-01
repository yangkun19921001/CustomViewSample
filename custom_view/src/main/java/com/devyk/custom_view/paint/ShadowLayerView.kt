package com.devyk.custom_view.paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat.setAlpha
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


/**
 * <pre>
 *     author  : devyk on 2019-11-29 22:21
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ShadowLayerView
 * </pre>
 */
class ShadowLayerView : View {
    val paint = Paint()
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun draw(canvas: Canvas) {
        super.draw(canvas)


        paint.setStyle(Paint.Style.FILL)
        paint.setColor(Color.BLACK)
        // 设置透明度，要在setColor后面设置才生效
        paint.setAlpha(80)
         // 如果不关闭硬件加速，setShadowLayer无效
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        // (阴影的半径，X轴方向上相对主体的位移，Y轴相对位移)
        paint.setShadowLayer(50f, 10f, 10f, Color.RED)
        paint.setTextSize(50f)

        // cx和cy为圆点的坐标
        val radius = 200
        val offest = 40

        val startX = width / 2 - radius
        val startY = height / 2
        canvas.drawText("画一个圆", width / 2 - 100f, height / 2f - 300, paint)
        canvas.drawCircle(startX.toFloat(), startY.toFloat(), radius.toFloat(), paint)

        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeWidth(5f)
        paint.setShadowLayer(50f, -20f, 10f, Color.RED)
        canvas.drawCircle(startX + radius * 2 + offest.toFloat(), startY.toFloat(), radius.toFloat(), paint)

    }

}