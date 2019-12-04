package com.devyk.custom_view.base

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import com.devyk.custom_view.utils.HelpDraw
import com.devyk.custom_view.utils.Utils

/**
 * <pre>
 *     author  : devyk on 2019-12-01 20:13
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is BaseView
 * </pre>
 */
open class BaseView : View {
    private var mGridPaint: Paint? = null//网格画笔
    private var mWinSize: Point? = null//屏幕尺寸
    private var mCoo: Point? = null//坐标系原点


    public var mViewWidth: Int = 0
    public var mViewHeight: Int = 0

    public var mTempPath = Path()
    public var mTempPaint = Paint(Paint.ANTI_ALIAS_FLAG)



    /**
     * 基础 Paint
     */
    public var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 坐标系中心点
     */
    private val mX = 0
    private val mY = 0


    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h
    }

    public open fun init(context: Context?, attrs: AttributeSet?) {
        //准备屏幕尺寸
        mWinSize = Point()
        mCoo = Point(mX, mY)
        Utils.loadWinSize(getContext(), mWinSize)
        mGridPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        mTempPaint.color = Color.RED
        mTempPaint.strokeWidth = 5f

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        HelpDraw.drawGrid(canvas, mWinSize, mGridPaint)
        HelpDraw.drawCoo(canvas, mCoo, mWinSize, mGridPaint)
    }
}