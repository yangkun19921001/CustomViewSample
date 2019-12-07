package com.devyk.customview

import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import kotlinx.android.synthetic.main.activity_svg.*


/**
 * <pre>
 *     author  : devyk on 2019-12-06 23:30
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is SVGDemo1Activity
 * </pre>
 */
class SVGDemo1Activity : AppCompatActivity() {


    var reStartTT = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            startAnimatabe(R.drawable.line_animated_toutiao, true)
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg)

        //水滴动画
        startWaterDropAnimator.setOnClickListener {
            startAnimatabe(R.drawable.line_animated_vector, false)
        }
        //搜索动画
        startSearchAnimator.setOnClickListener {
            startAnimatabe(R.drawable.line_animated_search, false)
        }
        //执行警车动画
        startPoliceCarAnimator.setOnClickListener {
            startAnimatabe(R.drawable.line_animated_car, false)
        }
        //执行头条动画
        startTTAnimator.setOnClickListener {
            startAnimatabe(R.drawable.line_animated_toutiao, true)
        }
    }

    private fun startAnimatabe(lineAnimatedVector: Int, isRegister: Boolean): Animatable {
        val animatedVectorDrawable = AnimatedVectorDrawableCompat.create(this, lineAnimatedVector)
        iv.setImageDrawable(animatedVectorDrawable)
        val animatable = iv.drawable as Animatable
        animatable.start()
        animatedVectorDrawable!!.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                super.onAnimationEnd(drawable)
                if (!isRegister) return
                animatedVectorDrawable.unregisterAnimationCallback(this)
                //重新开始在 xml 设置 restart 无效暂时用 Handler 实现了。
                reStartTT.sendEmptyMessage(0)

            }
        })
        return animatable

    }
}