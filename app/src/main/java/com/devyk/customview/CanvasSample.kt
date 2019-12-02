package com.devyk.customview

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.devyk.custom_view.utils.Utils
import kotlinx.android.synthetic.main.activity_canvas_sample.*

/**
 * <pre>
 *     author  : devyk on 2019-12-01 20:22
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CanvasSample
 * </pre>
 */
class CanvasSample : Activity() {

    private val mImgIds = intArrayOf(//7个
        R.drawable.avft,
        R.drawable.box_stack,
        R.drawable.bubble_frame,
        R.drawable.bubbles,
        R.drawable.bullseye,
        R.drawable.circle_filled,
        R.drawable.circle_outline,

        R.drawable.avft,
        R.drawable.box_stack,
        R.drawable.bubble_frame,
        R.drawable.bubbles,
        R.drawable.bullseye,
        R.drawable.circle_filled,
        R.drawable.circle_outline
    )
    private val mImgIds_active = intArrayOf(
        R.drawable.avft_active,
        R.drawable.box_stack_active,
        R.drawable.bubble_frame_active,
        R.drawable.bubbles_active,
        R.drawable.bullseye_active,
        R.drawable.circle_filled_active,
        R.drawable.circle_outline_active,
        R.drawable.avft_active,
        R.drawable.box_stack_active,
        R.drawable.bubble_frame_active,
        R.drawable.bubbles_active,
        R.drawable.bullseye_active,
        R.drawable.circle_filled_active,
        R.drawable.circle_outline_active
    )

   lateinit var revealDrawables: Array<Drawable?>


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        Utils.setActivityFullScreen(this.window)
        super.onCreate(savedInstanceState)
//        setContentView(CircleView(this))

        setContentView(R.layout.activity_canvas_sample)

//        initData()



    }



    private fun initData() {
        revealDrawables = arrayOfNulls<Drawable>(mImgIds.size)

        for (i in mImgIds.indices ) {
            val rd = com.devyk.custom_view.canvas.CustomDrawable(
                resources.getDrawable(mImgIds[i]),
                resources.getDrawable(mImgIds_active[i]),
                com.devyk.custom_view.canvas.CustomDrawable.HORIZONTAL
            )
            revealDrawables[i] = rd
        }
//        gallaryHorizonalScrollView.addImageViews(revealDrawables)
    }
}