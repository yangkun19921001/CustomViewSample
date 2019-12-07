package com.devyk.customview

import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg)

        //水滴动画
//        startAnimatabe(R.drawable.line_animated_vector)
        //搜索动画
        startSearchAnimator.setOnClickListener {
            startAnimatabe(R.drawable.line_animated_search)
        }
    }

    private fun startAnimatabe(lineAnimatedVector: Int) {
        val animatedVectorDrawable = AnimatedVectorDrawableCompat.create(this, lineAnimatedVector)
        iv.setImageDrawable(animatedVectorDrawable)
        val animatable = iv.drawable as Animatable
        animatable.start()
    }
}