package com.devyk.custom_view.svg.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.devyk.custom_view.svg.map.Dom2XmlUtils.Companion.MAP_RECTF



/**
 * <pre>
 *     author  : devyk on 2019-12-06 14:22
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MapView
 * </pre>
 */
class MapView : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 当前改变的宽高值
     */
    var mViewWidth = 0
    var mViewHeight = 0

    /**
     * 判断是否竖屏
     */
    var widthSize = -1
    var heightSize = -1


    /**
     * 当前 地图的宽高
     *
     */
    var mMapRectWidth = 0f
    var mMapRectHeight = 0f

    /**
     * 解析出来的 map 数据
     */
    var mapDataList = ArrayList<MapData>()

    /**
     * 绘制地图
     */
    var mPaintPath = Paint(Paint.ANTI_ALIAS_FLAG)
    var mPaintText = Paint(Paint.ANTI_ALIAS_FLAG)
    var mPaintTextTitle = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 平铺缩放比例
     */
    var scaleWidthValues = 0f;
    var scaleHeightValues = 0f;

    private var TAG = this.javaClass.simpleName

    /**
     * 进行初始化
     */
    private fun init(context: Context, attrs: AttributeSet) {
        mPaintText.setColor(Color.WHITE)
        mPaintText.setStyle(Paint.Style.FILL_AND_STROKE)
        mPaintText.setTextSize(12f)
        Log.d(TAG, "init")

        mPaintTextTitle.setColor(Color.RED)
        mPaintTextTitle.textSize = 50f

        //子线程解析 xml
        val thread = Thread {
            val inputStream = context.resources.openRawResource(com.devyk.custom_view.R.raw.chinahigh)
            val mapData = Dom2XmlUtils.dom2xml(inputStream)
            this.post {
                if (Dom2XmlUtils.MAP_RECTF != null) {
                    mMapRectWidth = Dom2XmlUtils.MAP_RECTF.width()
                    mMapRectHeight = Dom2XmlUtils.MAP_RECTF.height()
                    mapDataList.addAll(mapData)
                    //解决时而显示异常问题
                    measure(measuredWidth, measuredHeight)
                    postInvalidate()
                }
            }
        }.start()


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewHeight = h
        mViewWidth = w
    }


    /**
     * 开始测量
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量模式
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        //测量大小
        widthSize = MeasureSpec.getSize(widthMeasureSpec)
        heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (!MAP_RECTF.isEmpty && mMapRectHeight != 0f && mMapRectWidth != 0f) {
            //拿来到显示比例
            scaleHeightValues = heightSize / mMapRectHeight
            scaleWidthValues = widthSize / mMapRectWidth
        }

        //xml 文件中宽高 wrap_content
        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            //如果是横屏宽保留最大，高需要适配
            if (widthSize < heightSize && mMapRectHeight != 0f) {
                setMeasuredDimension(widthSize, (mMapRectHeight * scaleWidthValues).toInt())
            } else {
                setMeasuredDimension(widthSize, heightSize)
            }
        } else {
            setMeasuredDimension(widthSize, heightSize)
        }
    }

    /**
     * 开始绘制地图
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mapDataList != null && mapDataList.size > 0)
            drawMap(canvas)
    }

    /**
     * 绘制 Map 数据
     */
    @SuppressLint("Range")
    private fun drawMap(canvas: Canvas) {
        canvas.save()
        if (widthSize > heightSize) {
            canvas.scale(scaleWidthValues, scaleHeightValues)
        } else {
            canvas.scale(scaleWidthValues, scaleWidthValues)
        }

        mapDataList.forEach { data ->
            run {
                if (data.isSelect) {
                    drawPath(data, canvas, Color.RED)
                } else {
                    drawPath(data, canvas, Color.parseColor(data.fillColor))
                }
            }
        }
        canvas.restore()
        canvas.drawText("中国🇨🇳地图", widthSize / 2 - mPaintTextTitle.measureText("中国🇨🇳地图") / 2f, 100f, mPaintTextTitle)
    }

    /**
     * 开始绘制 Path
     */
    private fun drawPath(
        data: MapData,
        canvas: Canvas,
        magenta: Int
    ) {
        mPaintPath.setColor(magenta)
        mPaintPath.setStyle(Paint.Style.FILL)
        mPaintPath.setTextSize(30f)
        mPaintPath.setStrokeWidth(data.strokeWidth.toFloat())
        canvas.drawPath(data.pathData, mPaintPath)
        val rectF = RectF()
        data.pathData.computeBounds(rectF, true)
        canvas.drawText(
            if (data.name.isEmpty()) "" else data.name,
            rectF.centerX() - mPaintText.measureText(data.name) / 2,
            rectF.centerY(), mPaintText
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                handlerTouch(event.getX(), event.getY())
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 处理点击事件
     */
    private fun handlerTouch(x: Float, y: Float) {
        if (mapDataList.size == 0) return

        var xScale = 0f
        var yScale = 0f

        if (widthSize > heightSize) {
            xScale = scaleWidthValues
            yScale = scaleHeightValues
        } else {
            xScale = scaleWidthValues
            yScale = scaleWidthValues
        }
        mapDataList.forEach { data ->
            run {
                data.isSelect = false
                if (isTouchRegion(x / xScale, y / yScale, data.pathData)) {
                    data.isSelect = true
                    postInvalidate()
                }
            }
        }
    }


}

/**
 * 判断是否在点击区域内
 */
fun isTouchRegion(x: Float, y: Float, path: Path): Boolean {
    //创建一个矩形
    val rectF = RectF()
    //获取到当前省份的矩形边界
    path.computeBounds(rectF, true)
    //创建一个区域对象
    val region = Region()
    //将path对象放入到Region区域对象中
    region.setPath(path, Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt()))
    //返回是否这个区域包含传进来的坐标
    return region.contains(x.toInt(), y.toInt())
}




