package com.devyk.customview

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.Gravity

/**
 * <pre>
 *     author  : devyk on 2019-12-02 17:00
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CustomDrawable
 *
 *
 * </pre>
 */
public class CustomDrawable : Drawable {

    lateinit var unseleter: Drawable
    lateinit var selecter: Drawable

    constructor(unseleter: Drawable, selecter: Drawable) : super() {
        this.selecter = selecter
        this.unseleter = unseleter

    }


    override fun draw(canvas: Canvas) {
        //得到当前自身 Drawable 的矩形区域
        val bounds = bounds
        //1. 绘制灰色部分
        drawGrayDraw(bounds,canvas)
        //2. 绘制彩色部分
        drawColorDraw(bounds,canvas)
    }


    /**
     * 绘制灰色区域
     * @link https://www.cnblogs.com/over140/archive/2011/12/14/2287179.html
     */
    private fun drawGrayDraw(bound: Rect, canvas: Canvas) {
        val rect = Rect()
        Gravity.apply(
            Gravity.LEFT,//开始从左边或者右边开始抠图
            bound.width(),  //目标矩形的宽
            bound.height(), //目标矩形的高
            bound,//被扣出来的 rect
            rect //目标 rect
        )
        canvas.save() //保存当前画布
        canvas.clipRect(rect)
        unseleter.draw(canvas)
        canvas.restore()

    }

    /**
     * 绘制彩色区域
     */
    private fun drawColorDraw(bounds: Rect, canvas: Canvas) {
        val rect = Rect()
        Gravity.apply(
            Gravity.RIGHT,//开始从左边或者右边开始抠图
            bounds.width()/3,  //目标矩形的宽
            bounds.height(), //目标矩形的高
            bounds,//被扣出来的 rect
            rect //目标 rect
        )
        canvas.save() //保存当前画布
        canvas.clipRect(rect)
        selecter.draw(canvas)
        canvas.restore()

    }


    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int  = 0

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        //改变之后重新赋值
        unseleter.bounds = bounds
        selecter.bounds = bounds
    }

    override fun getIntrinsicHeight(): Int {
        return Math.max(selecter.intrinsicHeight, unseleter.intrinsicHeight)
    }

    override fun getIntrinsicWidth(): Int {
        return Math.max(selecter.intrinsicWidth, unseleter.intrinsicWidth)
    }

    override fun onLevelChange(level: Int): Boolean {
        //如果 level 改变，提醒自己需要重绘
        invalidateSelf()
        return super.onLevelChange(level)

    }

}