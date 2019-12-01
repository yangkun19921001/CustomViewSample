package com.devyk.custom_view.paint

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import com.devyk.custom_view.R

/**
 * <pre>
 *     author  : devyk on 2019-11-29 23:22
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MyGradientView
 * </pre>
 */
class MyGradientView : View {

    private var mPaint: Paint? = null
    private var mBitMap: Bitmap? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private val mColors = intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private fun init() {
        mBitMap = (resources.getDrawable(R.mipmap.girl_gaitubao) as BitmapDrawable).bitmap
        mPaint = Paint()
        mWidth = mBitMap!!.getWidth()
        mHeight = mBitMap!!.getHeight()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * TileMode.CLAMP 拉伸最后一个像素去铺满剩下的地方
         * TileMode.MIRROR 通过镜像翻转铺满剩下的地方。
         * TileMode.REPEAT 重复图片平铺整个画面（电脑设置壁纸）
         * 在图片和显示区域大小不符的情况进行扩充渲染
         */

        /**
         * 位图渲染，BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
         * Bitmap:构造shader使用的bitmap
         * tileX：X轴方向的TileMode
         * tileY:Y轴方向的TileMode
         */

        //1.
/*        val bitMapShader = BitmapShader(
            mBitMap!!, Shader.TileMode.MIRROR,
            Shader.TileMode.MIRROR
        )
        //设置图片效果
        mPaint!!.setShader(bitMapShader)
        //抗锯齿
        mPaint!!.setAntiAlias(true)

        //绘制圆
        canvas.drawCircle(width/2f,height/2f,mHeight.toFloat(),mPaint!!)*/

        //2.
        /**线性渲染
         * x0, y0, 起始点
         *  x1, y1, 结束点
         * int[]  mColors, 中间依次要出现的几个颜色
         * float[] positions 位置数组，position的取值范围[0,1]，作用是指定几个颜色分别放置在那个位置上，
         * 如果传null，渐变就线性变化。
         *    tile 用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
         */
/*        var linearGradient = LinearGradient(
            0f, 0f, 800f, 800f,
            mColors, null, Shader.TileMode.CLAMP
        )
//        var linearGradient = LinearGradient(0f, 0f, 400f, 400f, mColors, null, Shader.TileMode.REPEAT)
        mPaint!!.setShader(linearGradient)
        canvas.drawRect(0f, 0f, 800f, 800f, mPaint!!)*/


        /**
         * 3.
         * 环形渲染
         * centerX ,centerY：shader的中心坐标，开始渐变的坐标
         * radius:渐变的半径
         * centerColor,edgeColor:中心点渐变颜色，边界的渐变颜色
         * colors:渐变颜色数组
         * stops:渐变位置数组，类似扫描渐变的positions数组，取值[0,1],中心点为0，半径到达位置为1.0f
         * tileMode:shader未覆盖以外的填充模式
         */
/*        val mRadialGradient = RadialGradient(
            width/2f,height/2f,width/3.toFloat(),
            mColors, null, Shader.TileMode.REPEAT
        )
        mPaint!!.setShader(mRadialGradient)
        canvas.drawCircle(width/2f,height/2f,width/3.toFloat(), mPaint!!)*/


        /**
         * 扫描渲染
         * cx,cy 渐变中心坐标
         * color0,color1：渐变开始结束颜色
         * colors，positions：类似LinearGradient,用于多颜色渐变,positions为null时，根据颜色线性渐变
         */
//        val mSweepGradient = SweepGradient(width/2f,height/2f, mColors, null)
//        mPaint!!.setShader(mSweepGradient)
//        canvas.drawCircle(width/2f,height/2f,width/3.toFloat(), mPaint!!)


        /**
         * 组合渲染，
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, Xfermode mode)
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, PorterDuff.Mode mode)
         * shaderA,shaderB:要混合的两种shader
         * Xfermode mode： 组合两种shader颜色的模式
         * PorterDuff.Mode mode: 组合两种shader颜色的模式
         */
/*        val bitMapShader = BitmapShader(
            mBitMap!!, Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        )
        val linearGradient = LinearGradient(
            0f, 0f, 800f, 800f,
            mColors, null, Shader.TileMode.CLAMP
        )
        val mComposeShader = ComposeShader(linearGradient, bitMapShader, PorterDuff.Mode.SRC_OVER)
        mPaint!!.setShader(mComposeShader)
        canvas.drawRect(0f, 0f, 800f, 1000f, mPaint!!)*/


        /***************用ComposeShader即可实现心形图渐变效果*********************************/
        //创建BitmapShader，用以绘制心
        val mBitmap = (resources.getDrawable(R.mipmap.heart) as BitmapDrawable).bitmap
        val bitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        //创建LinearGradient，用以产生从左上角到右下角的颜色渐变效果
        val linearGradient = LinearGradient(
            0f, 0f, mWidth.toFloat(), mHeight.toFloat(),
            Color.BLUE, Color.RED, Shader.TileMode.CLAMP
        )
        //bitmapShader对应目标像素，linearGradient对应源像素，像素颜色混合采用MULTIPLY模式
        val composeShader = ComposeShader(linearGradient, bitmapShader, PorterDuff.Mode.MULTIPLY)
//         ComposeShader composeShader2 = new ComposeShader(composeShader, linearGradient, PorterDuff.Mode.MULTIPLY);
        //将组合的composeShader作为画笔paint绘图所使用的shader
        mPaint!!.setShader(composeShader)
        //用composeShader绘制矩形区域
        canvas.drawRect(0f, 0f, mBitmap.width.toFloat(), mBitmap.height.toFloat(), mPaint!!)
        //所谓渲染就是对于我们绘制区域进行按照上诉渲染规则进行色彩的填充

    }
}