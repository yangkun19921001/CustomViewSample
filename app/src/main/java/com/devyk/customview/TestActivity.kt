package com.devyk.customview

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.devyk.custom_view.flow.FlowLayout
import com.devyk.custom_view.flow.TagAdapter
import kotlinx.android.synthetic.main.activity_test.*


/**
 * <pre>
 *     author  : devyk on 2019-11-27 15:18
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is TestActivity
 * </pre>
 */
class TestActivity : Activity() {

    var TAG = this.javaClass.simpleName

    private val mVals = arrayOf(
        "Hello",
        "Android",
        "Weclome Hi ",
        "Button",
        "TextView",
        "Hello",
        "Android",
        "Weclome",
        "Button ImageView",
        "TextView",
        "Helloworld",
        "Android",
        "Weclome Hello",
        "Button Text",
        "TextView"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        mFlowLayout.setAdapter(object : TagAdapter<String>(mVals) {

            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(applicationContext).inflate(
                    R.layout.tv,
                    mFlowLayout, false
                ) as TextView
                tv.text = s
                Log.d(TAG,s);
                return tv
            }

            override fun setSelected(position: Int, s: String): Boolean {
                return s == "Android"
            }
        })



    }


}

