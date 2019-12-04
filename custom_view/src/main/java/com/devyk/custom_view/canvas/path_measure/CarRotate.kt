package com.devyk.custom_view.canvas.path_measure

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.devyk.custom_view.R
import com.devyk.custom_view.base.BaseView

/**
 * <pre>
 *     author  : devyk on 2019-12-04 10:50
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is AirplaneRotate 小飞机旋转
 * </pre>
 */
class CarRotate : BaseView {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 定义一个 Bitmap
     */
    var mBitmap: Bitmap? = null

    /**
     * 定义一个 path 测量
     */
    var mPathMeasure: PathMeasure? = null

    /**
     * 定义一个矩阵，目的是给 bitmap 修改角度
     */
    var mMatrix: Matrix? = null

    /**
     * 截取的变量值
     */
    var mCurValues = 0f

    private var pos: FloatArray? = null                // 当前点的实际位置
    private var tan: FloatArray? = null                // 当前点的tangent值,用于计算图片所需旋转的角度

    override fun init(context: Context?, attrs: AttributeSet?) {
        super.init(context, attrs)

        //初始化 bitmap
        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        mBitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.car, options)
        mPathMeasure = PathMeasure()
        mMatrix = Matrix()

        pos = FloatArray(2)
        tan = FloatArray(2)

        mTempPaint.style = Paint.Style.STROKE

    }


    /**
     * 实现 1
     */
/*    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //清楚 path 数据
        mTempPath.rewind()
        //绘制一个模拟公路
        addLineToPath()
        //测量 path,不闭合
        mPathMeasure!!.setPath(mTempPath, true)
        //动态变化的值
        mCurValues += 0.002f
        if (mCurValues >= 1) mCurValues = 0f
        //拿到当前点上的 正弦值
        mPathMeasure!!.getPosTan(mPathMeasure!!.length * mCurValues, pos, tan)
        //通过正弦值拿到当前弧度
        val y = tan!![1].toDouble()
        val x = tan!![0].toDouble()
        //拿到 bitmap 需要旋转的角度，之后将矩阵旋转
        var degrees = (Math.atan2(y, x) * 180f / Math.PI).toFloat()
        println("角度：$degrees")
        mMatrix!!.reset()
        mMatrix!!.postRotate(degrees, mBitmap!!.width / 2.toFloat(), mBitmap!!.height / 2.toFloat())
        mMatrix!!.postTranslate(pos!![0] - mBitmap!!.getWidth() / 2, pos!![1] - mBitmap!!.getHeight() / 2)
        //绘制Bitmap和path
        canvas!!.drawPath(mTempPath, mTempPaint)
        canvas!!.drawBitmap(mBitmap!!, mMatrix!!, mTempPaint)

        //重绘
        postInvalidate()
    }*/

    /**
     * 实现 2
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //清楚 path 数据
        mTempPath.rewind()
        //绘制一个模拟公路
        addLineToPath()
        //测量 path,不闭合
        mPathMeasure!!.setPath(mTempPath, true)
        //动态变化的值
        mCurValues += 0.002f
        if (mCurValues >= 1) mCurValues = 0f


        // 获取当前位置的坐标以及趋势的矩阵
        mPathMeasure!!.getMatrix(mPathMeasure!!.getLength() * mCurValues, mMatrix!!,
            (PathMeasure.TANGENT_MATRIX_FLAG or PathMeasure.POSITION_MATRIX_FLAG))
        // 将图片绘制中心调整到与当前点重合(偏移加旋转)
        mMatrix!!.preTranslate(-mBitmap!!.getWidth() / 2f, -mBitmap!!.getHeight() / 2f);



        //绘制Bitmap和path
        canvas!!.drawPath(mTempPath, mTempPaint)
        canvas!!.drawBitmap(mBitmap!!, mMatrix!!, mTempPaint)

        //重绘
        postInvalidate()

    }

    private fun addLineToPath() {
        mTempPath.moveTo(100f, 100f)
        mTempPath.lineTo(100f, 200f)
        mTempPath.lineTo(200f, 300f)
        mTempPath.lineTo(300f, 400f)
        mTempPath.lineTo(400f, 500f)
        mTempPath.lineTo(500f, 600f)
        mTempPath.lineTo(600f, 300f)
        mTempPath.lineTo(600f, 900f)
        mTempPath.lineTo(900f, 1200f)
        mTempPath.lineTo(1200f, 800f)
        mTempPath.lineTo(800f, 900f)
        mTempPath.lineTo(900f, 100f)
        mTempPath.close()
    }
}