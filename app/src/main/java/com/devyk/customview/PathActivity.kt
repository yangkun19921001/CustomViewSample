package com.devyk.customview

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.devyk.custom_view.canvas.path.PathView
import com.devyk.custom_view.canvas.path_measure.CarRotate
import com.devyk.custom_view.canvas.path_measure.PathMeasureView

/**
 * <pre>
 *     author  : devyk on 2019-12-03 16:18
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PathActivity
 * </pre>
 */

public class PathActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        setContentView(PathView(this))
//        setContentView(PathMeasureView(this))
        setContentView(CarRotate(this))
    }

}