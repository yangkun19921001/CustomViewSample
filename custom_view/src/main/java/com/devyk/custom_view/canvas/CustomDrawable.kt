package com.devyk.custom_view.canvas

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity

/**
 * <pre>
 *     author  : devyk on 2019-12-02 18:23
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CustomDrawable
 * </pre>
 */

class CustomDrawable(
    private val mUnselectedDrawable: Drawable,
    private val mSelectedDrawable: Drawable,
    private val mOrientation: Int
) :
    Drawable() {
    private val mTmpRect = Rect()

    override fun draw(canvas: Canvas) {
        // 绘制
        val level = level//from 0 (minimum) to 10000
        Log.d(TAG, "level=$level")
        //三个区间
        //右边区间和左边区间--设置成灰色
        if (level == 10000 || level == 0) {
            mUnselectedDrawable.draw(canvas)
        } else if (level == 5000) {//全部选中--设置成彩色
            mSelectedDrawable.draw(canvas)
        } else {
            //混合效果的Drawable
            /**
             * 将画板切割成两块-左边和右边
             */
            val r = mTmpRect
            //得到当前自身 Drawable 的矩形区域
            val bounds = bounds
            run {
                //1.先绘制灰色部分
                //level 0~5000~10000
                //比例
                //4680 / 5000   -1f
                val ratio = level / 5000f - 1f
                var w = bounds.width()
                if (mOrientation == HORIZONTAL) {
                    //我们要扣的宽度
                    w = (w * Math.abs(ratio)).toInt()
                    Log.d(TAG, "w=$w")
                }
                var h = bounds.height()
                if (mOrientation == VERTICAL) {
                    h = (h * Math.abs(ratio)).toInt()
                }

                val gravity = if (ratio < 0) Gravity.LEFT else Gravity.RIGHT
                //从一个已有的bounds矩形边界范围中抠出一个矩形r
                Gravity.apply(
                    gravity, //从左边还是右边开始抠
                    w, //目标矩形的宽
                    h, //目标矩形的高
                    bounds, //被抠出来的rect
                    r
                )//目标rect

                canvas.save()//保存画布
                canvas.clipRect(r)//切割
                mUnselectedDrawable.draw(canvas)//画
                canvas.restore()//恢复之前保存的画布
            }
            run {
                //2.再绘制彩色部分
                //level 0~5000~10000
                //比例
                val ratio = level / 5000f - 1f
                var w = bounds.width()
                if (mOrientation == HORIZONTAL) {
                    w -= (w * Math.abs(ratio)).toInt()
                }
                var h = bounds.height()
                if (mOrientation == VERTICAL) {
                    h -= (h * Math.abs(ratio)).toInt()
                }

                val gravity = if (ratio < 0) Gravity.RIGHT else Gravity.LEFT
                //从一个已有的bounds矩形边界范围中抠出一个矩形r
                Gravity.apply(
                    gravity, //从左边还是右边开始抠
                    w, //目标矩形的宽
                    h, //目标矩形的高
                    bounds, //被抠出来的rect
                    r
                )//目标rect

                canvas.save()//保存画布
                canvas.clipRect(r)//切割
                mSelectedDrawable.draw(canvas)//画
                canvas.restore()//恢复之前保存的画布
            }

        }

    }

    override fun onBoundsChange(bounds: Rect) {
        // 定好两个Drawable图片的宽高---边界bounds
        mUnselectedDrawable.bounds = bounds
        mSelectedDrawable.bounds = bounds
        Log.d(TAG, "onBoundsChange w = " + bounds.width())
    }

    override fun getIntrinsicWidth(): Int {
        //得到Drawable的实际宽度
        return Math.max(
            mSelectedDrawable.intrinsicWidth,
            mUnselectedDrawable.intrinsicWidth
        )
    }

    override fun getIntrinsicHeight(): Int {
        //得到Drawable的实际高度
        return Math.max(
            mSelectedDrawable.intrinsicHeight,
            mUnselectedDrawable.intrinsicHeight
        )
    }

    override fun onLevelChange(level: Int): Boolean {
        // 当设置level的时候回调---提醒自己重新绘制
        invalidateSelf()
        return true
    }

    override fun setAlpha(i: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return 0
    }

    companion object {

        private val TAG = this.javaClass.simpleName
        val HORIZONTAL = 1
        val VERTICAL = 2
    }
}