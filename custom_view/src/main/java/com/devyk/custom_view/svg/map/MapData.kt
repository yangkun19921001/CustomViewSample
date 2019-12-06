package com.devyk.custom_view.svg.map

import android.graphics.Path

/**
 * <pre>
 *     author  : devyk on 2019-12-06 14:26
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MapData 管理 Map Path 数据
 * </pre>
 */
data class MapData(
    val name: String = "",
    val fillColor: String = "",
    val strokeColor: String = "",
    val strokeWidth: String = "",
    val pathData: Path,
    var isSelect: Boolean = false
) {

}