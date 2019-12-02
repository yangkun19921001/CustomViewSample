package com.devyk.custom_view.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * <pre>
 *     author  : devyk on 2019-12-01 19:59
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is Utils
 * </pre>
 */
public class Utils {
    /**
     * 获得屏幕高度
     *
     * @param ctx 上下文
     * @param winSize 屏幕尺寸
     */
    public static void loadWinSize(Context ctx, Point winSize) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        winSize.x = outMetrics.widthPixels;
        winSize.y = outMetrics.heightPixels;
    }

    /**
     * 设置全屏
     * @param window
     */
    public static void  setActivityFullScreen(Window window) {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

    }

}
