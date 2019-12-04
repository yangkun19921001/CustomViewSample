package com.devyk.custom_view.canvas.path_measure

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.devyk.custom_view.base.BaseView
import android.graphics.RectF as RectF1

/**
 * <pre>
 *     author  : devyk on 2019-12-04 14:33
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is FacLoadingView
 * </pre>
 */


/**
 * 笑脸加载
 */
class FaceLoadingView :
    BaseView {
    /**
     * 眼睛跟嘴巴摆动的动画
     */
    internal var mAmin: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val offset = interpolatedTime * DURATION_AREA
            mMouchH = MOUCH_PERCENT_H + offset
            mMouchH2 = MOUCH_PERCENT_H2 + offset
            mEyesH = EYE_PERCENT_H + offset
            postInvalidate()
        }
    }
    private var reachedPath: Path? = null
    private val mPath = Path()


    private var mRadius: Float = 0.toFloat()

    private var mMouchH = MOUCH_PERCENT_H

    private var mMouchH2 = MOUCH_PERCENT_H2

    private var mEyesH = EYE_PERCENT_H

    private var isStart = true

    private var rectValues = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initView()
    }


    private fun startAni() {
        mAmin.duration = 500
        mAmin.repeatCount = Animation.INFINITE
        mAmin.repeatMode = Animation.REVERSE
        startAnimation(mAmin)

        //外框路径动画
        var valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.repeatCount = -1
        valueAnimator.addUpdateListener {
            rectValues = it.getAnimatedValue() as Float
        }
        valueAnimator.setDuration(1500)
        valueAnimator.start()
    }

    private fun initView() {
        mPaint = Paint()
        mPaint!!.color = Color.GRAY
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = dp2px(2f)
        mPaint!!.strokeJoin = Paint.Join.ROUND
        mPaint!!.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (isStart) {
            startAni()
            isStart = false
        }
        mRadius = mViewWidth.toFloat() / 7f / 6f
        reachedPath = Path()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(Color.TRANSPARENT)
        canvas.translate(mViewWidth / 2f, mViewHeight / 2f)
        canvas.save()
        //draw face
        drawFace(canvas)
        //drawreached rect
        drawReachedRect(canvas)
        canvas.restore()
    }

    /**
     * draw face
     */
    private fun drawFace(canvas: Canvas) {
        mPath.reset()
        mPaint!!.style = Paint.Style.FILL
        //画左边的眼睛
        mPath.addCircle(width / 5 * EYE_PERCENT_W, height / 10 * mEyesH - mRadius, mRadius, Path.Direction.CW)
        //画右边的眼睛
        mPath.addCircle(width / 5 * (1 - EYE_PERCENT_W), height / 10 * mEyesH - mRadius, mRadius, Path.Direction.CW)
        //画嘴巴
        mPath.moveTo(width / 5 * MOUCH_PERCENT_W, height / 10 * mMouchH)
        mPath.addArc(RectF1(50f, 150f, 150f, 200f), 10f, 50 * 3f)
        mPaint!!.style = Paint.Style.STROKE
        canvas.drawPath(mPath, mPaint!!)
    }

    /**
     *  draw reachedRect
     */
    private fun drawReachedRect(canvas: Canvas) {
        mPaint!!.style = Paint.Style.STROKE

        var rectF = RectF1()
        mPath.computeBounds(rectF, true)

        //绘制边界
        reachedPath!!.addRoundRect(
            rectF,
            20f,
            20f,
            Path.Direction.CW
        )
        //测量 path
        val measure = PathMeasure(reachedPath, true)
        //拿到长度
        val length = measure.length
        val path2 = Path()
        var start = length * rectValues / 2
        //截取部分 Path 执行动画
        measure.getSegment(start, length * rectValues, path2, true)
        canvas.drawPath(path2, mPaint!!)

    }

    fun dp2px(dpValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics)
    }


    companion object {
        /**
         * 左眼距离左边的距离（控件宽度＊EYE_PERCENT_W），
         * 右眼距离右边的距离（控件宽度＊EYE_PERCENT_W）
         */
        private val EYE_PERCENT_W = 0.8f
        /**
         * 眼睛距离top的距离（控件的高度＊EYE_PERCENT_H）
         */
        private val EYE_PERCENT_H = 0.38f
        /**
         * 嘴巴左边跟右边距离top的距离（控件的高度＊MOUCH_PERCENT_H）
         */
        private val MOUCH_PERCENT_H = 0.55f
        /**
         * 嘴巴中间距离top的距离（控件的高度＊MOUCH_PERCENT_H2）
         */
        private val MOUCH_PERCENT_H2 = 0.7f
        /**
         * 嘴巴左边跟右边距离边缘的位置（控件宽度＊MOUCH_PERCENT_W）
         */
        private val MOUCH_PERCENT_W = 0.23f
        /**
         * 眼睛跟嘴巴摆动的区间范围
         */
        private val DURATION_AREA = 0.15f
    }
}
