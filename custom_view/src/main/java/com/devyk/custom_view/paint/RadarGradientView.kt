package com.devyk.custom_view.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * <pre>
 *     author  : devyk on 2019-11-30 18:50
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RadarGradientView 渐变渲染/梯度渲染
 * </pre>
 */
class RadarGradientView : View {


    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private val    TAG = javaClass.simpleName

    //五个圆
    private val pots = floatArrayOf(0.05f, 0.1f, 0.15f, 0.2f, 0.25f, 0.3f, 0.35f)

    private var scanShader: Shader? = null // 扫描渲染shader
    private val scanSpeed = 10 // 扫描速度
    private var scanAngle: Int = 0 // 扫描旋转的角度

    private lateinit var mMatrix: Matrix // 旋转需要的矩阵

    private  var mPaintCircle = Paint() // 画圆用到的paint
    private  var mPaintRadar = Paint() // 扫描用到的paint

    private val run = object : Runnable {
        override fun run() {
            scanAngle = (scanAngle + scanSpeed) % 125 //
            Log.d(TAG,"scanAngle:$scanAngle")
            mMatrix.postRotate(scanSpeed.toFloat(), (mWidth / 2).toFloat(), (mHeight / 2).toFloat()) // 旋转矩阵
            invalidate() // 通知view重绘
            postDelayed(this, 50) // 调用自身 重复绘制
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        mMatrix = Matrix()
        // 画圆用到的paint
        mPaintCircle = Paint()
        mPaintCircle.style = Paint.Style.STROKE // 描边
        mPaintCircle.strokeWidth = 1f // 宽度
        mPaintCircle.alpha = 100 // 透明度
        mPaintCircle.isAntiAlias = true // 抗锯齿
        mPaintCircle.color = Color.parseColor("#B0C4DE") // 设置颜色 亮钢兰色

        // 扫描用到的paint
        mPaintRadar = Paint()
        mPaintRadar.style = Paint.Style.FILL_AND_STROKE // 填充
        mPaintRadar.isAntiAlias = true // 抗锯齿


        post(run)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        Log.d(TAG,"onDraw()")
        for (i in pots.indices) {
            canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mWidth * pots[i], mPaintCircle)
        }

        // 画布的旋转变换 需要调用save() 和 restore()
        canvas.save()

        scanShader = SweepGradient(
            (mWidth / 2).toFloat(), (mHeight / 2).toFloat(),
            intArrayOf(Color.TRANSPARENT, Color.parseColor("#84B5CA")), null
        )
        mPaintRadar.shader = scanShader // 设置着色器
        canvas.concat(mMatrix)
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mWidth * pots[6], mPaintRadar)

        canvas.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG,"onMeasure()")
        // 取屏幕的宽高是为了把雷达放在屏幕的中间
        mWidth = measuredWidth
        mHeight = measuredHeight
        mHeight = Math.min(mWidth, mHeight)
        mWidth = mHeight
    }

}