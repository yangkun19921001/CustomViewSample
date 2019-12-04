package com.devyk.custom_view.canvas.path

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import com.devyk.custom_view.base.BaseView
import android.view.MotionEvent
import android.widget.Toast
import androidx.annotation.RequiresApi


/**
 * <pre>
 *     author  : devyk on 2019-12-03 16:11
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PathView
 * </pre>
 */
public class PathView : BaseView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    lateinit var mPathPaint: Paint

    lateinit var mPath: Path

    var region = Region()

    override fun init(context: Context?, attrs: AttributeSet?) {
        super.init(context, attrs)
        mPathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPathPaint.style = Paint.Style.STROKE
        mPathPaint.strokeWidth = 5f
        mPathPaint.setColor(Color.RED)

        mPath = Path()

        /**
         * 1. reset, rewind (清除Path中的内容 reset 不保留内部数据结构，但会保留 FillType. rewind 会保留内部的数据结构，但不保留FillType)
         * 2. lineTo:
         *
         *
         *
         *
         */

    }


    /**
     * 开始绘制
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //清除Path中的内容 reset 不保留内部数据结构，但会保留 FillType
        mPath.reset()

        /**
         * 1. setLastPoint
         */

/*        //从0.0 连接 400，600
        mPath.lineTo(400f,600f)
        //重置上一点相当于 0，0 到 600，200, 设置之前操作的最后一个点位置(会影响之前跟之后的起始点)
        mPath.setLastPoint(600f,200f)
        //启动为 600，200 启动
        mPath.lineTo(900f,100f)*/


        /**2.moveTo*/

/*        //moveTo 设置起点
        mPath.moveTo(600f,200f)
        //从0.0 连接 400，600
        mPath.lineTo(400f,600f)
        mPath.lineTo(800f,300f)
        //最后一点和起点封闭
        mPath.close()*/

        /**
         * 3. close
         */
        /*
        //从0.0 连接 400，600
        mPath.lineTo(400f,600f)
        //moveTo 设置起点
//        mPath.moveTo(600f,200f)
        //启动为 600，200 启动
        mPath.lineTo(900f,100f)
        //最后一点和起点封闭
        mPath.close()*/

        /**
         * 4.addRect
         * @param CW  -> 顺时针
         * @param CCW -> 逆时针
         */
//        mPath.addRect(400f,400f,800f,800f,Path.Direction.CW)


        mPath.addCircle(500f, 500f, 150f, Path.Direction.CW)


        canvas!!.drawPath(mPath, mPathPaint)    // 绘制Path


        canvas.drawRect(300f,800f,800f,1300f ,mPathPaint)   // 绘制边界


//        mPath.moveTo(100f,900f)
//        mPath.cubicTo(100f,900f,(800-100)/2f,900 + 900/2f,800f,900f)




/*        mPathPaint.textSize = 50f
        //1. 添加矩形到 Path
        mPath.addRect(100f,300f,400f,700f,Path.Direction.CW)//顺时针
        canvas!!.drawText("1",200f,500f,mPathPaint)

        //2. 添加 圆角矩形到 Path
        mPath.addRoundRect(100f + 500,300f,1000f ,700f,30f,30f,Path.Direction.CCW)//逆时针
        canvas!!.drawText("2",800f,500f,mPathPaint)

        //3. 添加 椭圆 到 Path
        mPath.addOval(100f,1300f,600f ,1000f,Path.Direction.CCW)//逆时针
        canvas!!.drawText("3",300f,1150f,mPathPaint)

        //4. 添加 圆 到 Path
        mPath.addCircle(850f,1200f ,150f,Path.Direction.CCW)//逆时针
        canvas!!.drawText("4",850f,1200f,mPathPaint)

        //5. 添加 圆弧 到 Path ,直接添加一个圆弧到path中
        //添加一个圆弧到 path，如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
        mPath.addArc(100f,1500f,600f,1800f,0f,300f)
        canvas!!.drawText("5",300f,1550f,mPathPaint)

        mPath.arcTo(650f,1500f,800f,1800f,0f,180f,true)
        canvas!!.drawText("6",750f,1550f,mPathPaint)

        canvas!!.drawPath(mPath, mPathPaint)*/
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_UP -> {
                val rectF = RectF()
                mPath.computeBounds(rectF, true)
                region.setPath(
                    mPath,
                    Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
                )


                if (region.contains(event.x.toInt(), event.y.toInt())) {
                    Toast.makeText(context, "点击了圆", Toast.LENGTH_SHORT).show()
                }

                region.set(300, 800, 800, 1300)
                if (region.contains(event.x.toInt(), event.y.toInt())) {
                    Toast.makeText(context, "点击了矩形", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onTouchEvent(event)
    }
}