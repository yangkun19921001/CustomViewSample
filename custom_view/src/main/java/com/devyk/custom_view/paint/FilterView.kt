package com.devyk.custom_view.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-11-30 20:50
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is FilterView
 * </pre>
 */

class FilterView : View {

    private var paint = Paint()

    lateinit var bitmap: Bitmap

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 显示的高
     */
    var showHeight = 0

    init {

        init()
    }

    private fun init() {

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED

        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.gild_3)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        //关闭单个View的硬件加速功
//        //        setLayerType(View.LAYER_TYPE_SOFTWARE,null);




        //1. 缩放运算---乘法 -- 颜色增强
        val colorMartrix = ColorMatrix(
            floatArrayOf(
                1.2f, 0f, 0f, 0f, 0f,
                0f, 1.2f, 0f, 0f, 0f,
                0f, 0f, 1.2f, 0f, 0f,
                0f, 0f, 0f, 1.2f, 0f
            )
        )
        val rectF = RectF(
                0f,
            showHeight.toFloat() ,
                (bitmap.width / 2).toFloat(),
                (bitmap.height / 4).toFloat()
            )
        drawFilterBitmap(colorMartrix, canvas,rectF)

        showHeight += bitmap.height / 4



        //2 平移运算---加法
     var colorMartrix2 = ColorMatrix(floatArrayOf(
                        1f, 0f,0f,0f,0f,
                        0f,1f,0f,0f,100f,
                        0f,0f,1f,0f,0f,
                        0f,0f,0f,1f,0f
                    ))

        val rectF2 = RectF(
            0f,
            showHeight.toFloat(),
            (bitmap.width / 2).toFloat(),
            (bitmap.height /4) * 2.toFloat()
        )
        drawFilterBitmap(colorMartrix2, canvas,rectF2)


        showHeight += bitmap.height / 4



        //3. 反相效果 -- 底片效果
               var colorMartrix3 =  ColorMatrix(floatArrayOf(
                        -1f, 0f,0f,0f,255f,
                        0f,-1f,0f,0f,255f,
                        0f,0f,-1f,0f,255f,
                        0f,0f,0f,1f,0f
                   ));

        val rectF3 = RectF(
            0f,
            showHeight.toFloat(),
            (bitmap.width / 2).toFloat(),
            (bitmap.height /4) * 3.toFloat()
        )
        drawFilterBitmap(colorMartrix3, canvas,rectF3)


        /**
         * 4.黑白照片
         * 是将我们的三通道变为单通道的灰度模式
         * 去色原理：只要把R G B 三通道的色彩信息设置成一样，那么图像就会变成灰色，
         * 同时为了保证图像亮度不变，同一个通道里的R+G+B =1
         */
                var colorMartrix4 =  ColorMatrix(floatArrayOf(
                        0.213f, 0.715f,0.072f,0f,0f,
                        0.213f, 0.715f,0.072f,0f,0f,
                        0.213f, 0.715f,0.072f,0f,0f,
                        0f,0f,0f,1f,0f
            ));


        showHeight += bitmap.height / 4
        val rectF4 = RectF(
            bitmap.width/2f,
            bitmap.height /2f,
            (bitmap.width).toFloat(),
            (bitmap.height /4) * 3.toFloat()
        )
        drawFilterBitmap(colorMartrix4, canvas,rectF4)



         //5.发色效果---（比如红色和绿色交换）
                var colorMartrix5 =  ColorMatrix(floatArrayOf(
                        1f,0f,0f,0f,0f,
                        0f, 0f,1f,0f,0f,
                        0f,1f,0f,0f,0f,
                        0f,0f,0f,0.5F,0f
                    ));

        val rectF5 = RectF(
            bitmap.width / 2f,
            0f,
            (bitmap.width / 2 * 2).toFloat(),
            (bitmap.height /4) .toFloat()
        )
        drawFilterBitmap(colorMartrix5, canvas,rectF5)



        //6.复古效果
       var colorMartrix6=  ColorMatrix(floatArrayOf(
                        1/2f,1/2f,1/2f,0f,0f,
                        1/3f, 1/3f,1/3f,0f,0f,
                        1/4f,1/4f,1/4f,0f,0f,
                        0f,0f,0f,1f,0f
                    ));

        val rectF6 = RectF(
            bitmap.width / 2f,
            bitmap.height /4f,
            (bitmap.width / 2 * 2).toFloat(),
            (bitmap.height /4 * 2) .toFloat()
        )
        drawFilterBitmap(colorMartrix6, canvas,rectF6)



    }

    private fun drawFilterBitmap(colorMartrix: ColorMatrix, canvas: Canvas,rectF: RectF) {

        paint.colorFilter = ColorMatrixColorFilter(colorMartrix)
        canvas.drawBitmap(bitmap, null, rectF, paint)
    }


}
