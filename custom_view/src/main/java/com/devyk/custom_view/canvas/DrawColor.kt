package com.devyk.custom_view.canvas

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.graphics.Canvas.ALL_SAVE_FLAG
import android.os.Build
import android.util.AttributeSet
import com.devyk.custom_view.base.BaseView
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.RequiresApi
import com.devyk.custom_view.R


/**
 * <pre>
 *     author  : devyk on 2019-12-01 21:18
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is DrawColor
 * </pre>
 */
class DrawColor : BaseView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    lateinit var mPicture: Picture
    lateinit var mBitmap: Bitmap
    override fun init(context: Context?, attrs: AttributeSet?) {
        super.init(context, attrs)
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPicture = Picture()

        mBitmap =  BitmapFactory.decodeResource(context!!.resources, R.mipmap.girl_gaitubao)

        // 调用录制
        recording();


    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        //1.
//        canvas.drawColor(Color.RED)
        //2.
//        canvas.drawARGB(0xff,0xff,0,0x00)
        //3.
//        canvas.drawRGB(0xff,0x00,0x00)

        mPaint.strokeWidth = 30f
        mPaint.setColor(Color.RED)
//        //1.
//        canvas.drawLine(100f,100f,600f,600f,mPaint)
//        //2.
//        canvas.drawLines(floatArrayOf(
//            100f,100f,600f,600f
//        ),mPaint)
//        //3.
//        canvas.drawLines(floatArrayOf(
//            100f,100f,600f,600f
//        ),0,4,mPaint)
//
//
//        val pts = floatArrayOf(
//            50f, 50f, 400f, 50f,
//            400f, 50f, 400f, 600f,
//            400f, 600f, 50f, 600f,
//            60f, 600f, 50f, 50f
//        )
//    canvas.drawLines(pts, 0, 8, mPaint)

//
//        //1
//        canvas.drawPoint(100f, 100f, mPaint)
//
//        //2.
//        var offset = 0
//        var pts = floatArrayOf(
//            500f + offset, 100f, 500f + offset, 200f,
//            500f + offset, 300f, 500f + offset, 600f,
//            500f + offset, 700f, 500f + offset, 800f,
//            500f + offset, 900f, 500f + offset, 1000f
//        )
//        mPaint.setColor(Color.BLUE)
//        canvas.drawPoints(pts, 0, 16, mPaint)
//
//        //3.
//        mPaint.setColor(Color.GREEN)
//        offset = 100
//        pts = floatArrayOf(
//            500f + offset, 100f, 500f + offset, 200f,
//            500f + offset, 300f, 500f + offset, 600f,
//            500f + offset, 700f, 500f + offset, 800f,
//            500f + offset, 900f, 500f + offset, 1000f
//        )
//        canvas.drawPoints(pts, mPaint)

/*        //1.
        var rect = RectF(100.50f,100.50f,500.50f,500.50f)
        mPaint.style = Paint.Style.FILL
        canvas.drawRect(rect,mPaint)

        //2.

        var rect2 = Rect(300,300,600,600)
        mPaint.style = Paint.Style.FILL
        mPaint.setColor(Color.BLUE)
        mPaint.alpha = 100
        canvas.drawRect(rect2,mPaint)

        //3.
        mPaint.style = Paint.Style.FILL
        mPaint.setColor(Color.YELLOW)
        mPaint.alpha = 100
        canvas.drawRect(500f,500f,1000f,1000f,mPaint)*/

/*        */
        /**
         * 1. 绘制路径线
         *//*
        var path = Path()
        //1. 设置起始点
        path.moveTo(100f, 100f)
        //2. 第二条线的起点就是moveTo 设置的启动
        path.lineTo(100f,300f)
        //3. 第三条线的起点就是第二条的终点，依次类推
        path.lineTo(300f,500f)
        path.lineTo(500f,200f)
        //4. 闭合
        path.close()
        canvas.drawPath(path, mPaint)


        */
        /**
         * 2. 绘制弧度路径
         *//*
        var path2 = Path()
        //绘制弧度的起始位置
        path2.moveTo(100f,600f)
        var rectF = RectF(100f,600f,600f,1000f)
        //第一个参数生成椭圆的矩形，第二个参数是弧开始的角度以 X 轴正方向为 0 度，第三个参数是弧持续的角度
        path2.arcTo(rectF,0f,90f)
        canvas.drawPath(path2, mPaint)*/


/*        */
        /**
         * 1. 绘制椭圆
         *//*
        canvas.drawOval(RectF(100f,500f,600f,800f),mPaint)

        */
        /**
         * 2. 绘制圆
         *//*
        mPaint.setColor(Color.YELLOW)
        mPaint.alpha = 100
        canvas.drawCircle(400f,400f,200f,mPaint)*/


//        val bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.gild_3)
//        canvas.drawBitmap(bitmap,100f,100f,mPaint)


/*        */
        /**
         * 1.
         * 取 0 ~ 5  位置的 text 进行绘制
         *//*
        mPaint.textSize = 100f
        canvas.drawText(charArrayOf('1','2','3','4','5'),0,5,200f,200f,mPaint)

        */
        /**
         * 2.
         *//*
        canvas.drawText("12345",300f,300f,mPaint)

        */
        /**
         * 3.取 0 ~ 5  位置的 text 进行绘制
         *//*
        canvas.drawText("12345",0,5,400f,400f,mPaint)*/


/*        mPaint.setColor(Color.GREEN)
        mPaint.alpha = 100

        mPaint.textSize = 100f
        var path = Path()
        //1. 设置起始点
        path.moveTo(300f, 300f)
        //2. 第二条线的起点就是moveTo 设置的启动
        path.lineTo(300f,500f)
        //3. 第三条线的起点就是第二条的终点，依次类推
        path.lineTo(500f,800f)
        path.lineTo(800f,200f)
        //4. 闭合
        path.close()
        canvas.drawPath(path,mPaint)
        //从0偏移100px的像素
        canvas.drawTextOnPath("12345asodnaspdnfpoashfeuinfapjn",path,0f,100f,mPaint)*/


//        mPicture.draw(canvas)


        var rectF = RectF(500f, 100f, 900f, 500f)
/*        *//**
         * 1. 绘制弧
         * @param ovar : 矩形坐标
         * @param startAngle : 开始的角度
         * @param sweepAngle : 结束的角度
         * @param userCenter : 如果为true，则将椭圆的中心包括在圆弧中
         * @param paint : 画笔
         *//*
        canvas.drawArc(rectF, 0f, 90f, true, mPaint)

        *//**
         * 2. 绘制弧
         *//*
        canvas.drawArc(100f,500f,500f,900f,0f,90f,false,mPaint)*/
/*
        *//**
         * 1. 根据 RectF 绘制圆角矩形
         * @param rx: x 轴上的圆角半径
         * @param ry: y 轴上的圆角半径
         *//*
        canvas.drawRoundRect(rectF,50f,50f,mPaint)
        *//**
         * 2. 根据输入矩形位置绘制圆角矩形
         *//*
        canvas.drawRoundRect(100f,600f,500f,900f,100f,100f,mPaint)*/


/*        *//**
         * 1. 原始矩形
         *//*
        mPaint.color = Color.RED
        mPaint.alpha = 100
        canvas.drawRoundRect(rectF,50f,50f,mPaint)


        *//**
         * 2.将原始图形缩小 0.5 倍
         *//*
        var rectF2 = RectF(100f, 100f, 500f, 500f)
        mPaint.color = Color.BLUE
        mPaint.alpha = 100
        canvas.scale(0.5f,0.5f)
        canvas.drawRoundRect(rectF2,50f,50f,mPaint)    */

/*        *//**
         * 1. 原始矩形
         *//*
        mPaint.color = Color.RED
        mPaint.alpha = 100
        canvas.drawRoundRect(rectF,50f,50f,mPaint)


        *//**
         * 2.将原始图形旋转 90°
         *//*
        mPaint.color = Color.BLUE
        mPaint.alpha = 100
        canvas.rotate(45f)
        canvas.drawRoundRect(rectF,50f,50f,mPaint)

        *//**
         * 3.将原始图形旋转 28°
         * 以 坐标点 500，100 顺时针旋转 280°
         *//*
        mPaint.color = Color.YELLOW
        mPaint.alpha = 100
        canvas.rotate(280f,500f,100f)
        canvas.drawRoundRect(rectF,50f,50f,mPaint)*/


/*        *//**
         * 1. 原始图形
         *//*
        mPaint.color = Color.RED
        mPaint.alpha = 100
        canvas.drawRoundRect(rectF,50f,50f,mPaint)
        *//**
         * 2.图层开始错切
         *//*
        canvas.skew(0f,0.5f)
        mPaint.color = Color.BLUE
        mPaint.alpha = 100
        canvas.drawRoundRect(rectF,50f,50f,mPaint)*/

/*
        *//**
         * 原始图形
         *//*
        canvas.drawBitmap(mBitmap,100f,100f,mPaint)
        *//**
         *1. 矩阵平移 500，500
         *//*
        var matrix = Matrix()
        matrix.setTranslate(500f,500f)
        canvas.drawBitmap(mBitmap,matrix,mPaint)

        *//**
         * 2. 矩阵缩放 0.5 倍
         *//*
        var matrix2 = Matrix()
        matrix2.setScale(0.5f,0.5f)
        canvas.drawBitmap(mBitmap,matrix2,mPaint)

        *//**
         * 3. 矩阵旋转 45°
         *//*
        var matrix3 = Matrix()
        matrix3.setRotate(125f,500f,500f)
        canvas.drawBitmap(mBitmap,matrix3,mPaint)

        *//**
         * 4. 错切
         *//*
        var matrix4 = Matrix()
        matrix4.setSkew(0.5f,0.5f)
        canvas.drawBitmap(mBitmap,matrix4,mPaint)*/

/*
        *//**
         * 1. 原始图形
         *//*
        mPaint.color = Color.RED
        mPaint.alpha = 100
        canvas.drawRect(300f,300f,700f,700f,mPaint)
        canvas.drawText("1.原始",400f,600f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 100f
            it.color = Color.WHITE
        })
        *//**
         * 2. 在 RectF 矩形区域裁剪一块画布，绘制图形只能在该区域中绘制
         *//*
        var rectf2 = RectF(100f, 100f , 500f, 500f);
        canvas.clipRect(rectf2)
        mPaint.color = Color.BLUE
        mPaint.alpha = 100
        canvas.drawColor(mPaint.color)
        canvas.drawText("2.clip",200f,200f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 100f
            it.color = Color.WHITE
        })

        *//**
         * 3. 在 300，300；700，700 坐标点上绘制矩形
         *//*
        mPaint.color = Color.YELLOW
        mPaint.alpha = 100
        canvas.drawRect(300f,300f,700f,700f,mPaint)
        canvas.drawText("3.裁剪之后",350f,400f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 30f
            it.color = Color.WHITE
        }) */
/*
        *//**
         * 1. 原始图形
         *//*
        mPaint.color = Color.RED
        mPaint.alpha = 100
        canvas.drawRect(300f,300f,700f,700f,mPaint)
        canvas.drawText("1.原始",400f,600f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 100f
            it.color = Color.WHITE
        })
        *//**
         * 2. 在 RectF 矩形区域裁剪一块画布，绘制图形只能在该区域中绘制
         *//*
        var rectf2 = RectF(100f, 100f , 500f, 500f);
        //将未裁剪的图层先进行保存
        canvas.save()
        canvas.clipRect(rectf2)
        mPaint.color = Color.BLUE
        mPaint.alpha = 100
        canvas.drawColor(mPaint.color)
        canvas.drawText("2.clip",200f,200f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 100f
            it.color = Color.WHITE
        })

        *//**
         * 3. 在 300，300；700，700 坐标点上绘制矩形
         *//*
        //裁剪完之后出栈
        canvas.restore()
        mPaint.color = Color.YELLOW
        mPaint.alpha = 100
        canvas.drawRect(300f,300f,600f,600f,mPaint)
        canvas.drawText("3.裁剪之后",350f,400f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 30f
            it.color = Color.WHITE
        })*/

        /**
         * 1. 原始图像
         */
        mPaint.color = Color.RED
        mPaint.alpha = 200
        canvas.drawRect(300f,300f,1000f,1000f,mPaint)
        canvas.drawText("1. 裁剪图层",750f,750f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 30f
            it.color = Color.WHITE
        })
        /**
         * 2. 保存
         */
        val saveLayer = canvas.saveLayer(300f, 300f, 1000f, 1000f, mPaint,ALL_SAVE_FLAG)
        mPaint.color = Color.BLUE
        mPaint.alpha = 100
        canvas.drawCircle(500f,500f,300f,mPaint)
        canvas.drawText("2. 绘制圆",350f,700f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 30f
            it.color = Color.WHITE
        })

        /**
         * 3.恢复图层
         */
        canvas.restoreToCount(saveLayer)
        /**
         *
         */
        mPaint.color = Color.BLUE
        mPaint.alpha = 100
        canvas.drawCircle(400f,400f,200f,mPaint)
        canvas.drawText("3. 恢复",350f,250f,Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.textSize = 30f
            it.color = Color.WHITE
        })




    }



    private fun recording() {

        // 开始录制
        val canvas = mPicture.beginRecording(500, 500)
        // 创建一个画笔
        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        // 在Canvas中具体操作
        canvas.translate(250f, 250f)
        // 绘制一个圆
        canvas.drawCircle(0f, 0f, 100f, paint)
        //结束录制
        mPicture.endRecording()
    }
}