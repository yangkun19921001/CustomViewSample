package com.devyk.customview

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_custom_viewgroup.*
import java.util.*

/**
 * <pre>
 *     author  : devyk on 2019-11-27 17:27
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CustomViewGroup
 * </pre>
 */
class CustomViewGroup : Activity() {

    val content = mutableListOf<String>("中","她其实的","我愿意","除了七仙女以外","当然最好是嫁一个又优秀又","尤其是封建社会","何况别人呢","但后来她实际上还是有很多机会的",
        "是一旦发现了就会被沉塘的死罪","是什么让温莎公爵舍弃江山和王位？难道真的是他们幼稚或一时冲动")


    val contentColor = mutableListOf<Int>(Color.BLUE,Color.RED,Color.GREEN,Color.DKGRAY,Color.BLACK,
        Color.YELLOW,Color.RED,Color.MAGENTA,Color.LTGRAY,Color.CYAN)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_viewgroup)

        var lists =  getMutableList()
        lists.forEach {
         wf.addView(it)
        }


    }

    private fun getMutableList(): MutableList<TextView> {
        var testViews = mutableListOf<TextView>()


        for (index in 0..9){
            testViews.add( TextView(this).also {
                it.setText(content.get(index))
                it.setBackgroundResource(R.drawable.flag_01)
                it.setTextColor(contentColor.get(index))
            })
        }



        return testViews

    }


}