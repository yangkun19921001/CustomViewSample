package com.devyk.customview

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager

/**
 * <pre>
 *     author  : devyk on 2019-12-06 15:23
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MapActivity 地图显示控件
 * </pre>
 */

public class MapActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_map)
    }
}