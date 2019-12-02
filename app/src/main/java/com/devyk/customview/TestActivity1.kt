package com.devyk.customview

import android.app.Activity
import android.app.Dialog
import android.os.Bundle

/**
 * <pre>
 *     author  : devyk on 2019-11-29 22:18
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is TestActivity1
 * </pre>
 */
public class TestActivity1 : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_test_1)
//        setContentView(R.layout.activity_test_1)


        var dialot  = Dialog(this)
        dialot.setTitle("你好")
        dialot.show()
        dialot.window!!.setBackgroundDrawableResource(R.drawable.shade)

    }
}