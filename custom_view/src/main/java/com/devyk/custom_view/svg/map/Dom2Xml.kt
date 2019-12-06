package com.devyk.custom_view.svg.map

import android.graphics.RectF
import android.icu.util.RangeValueIterator
import com.devyk.custom_view.svg.PathParser
import org.w3c.dom.Element
import java.io.InputStream
import java.util.ArrayList
import javax.xml.parsers.DocumentBuilderFactory

/**
 * <pre>
 *     author  : devyk on 2019-12-06 14:49
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is Dom2Xml
 * </pre>
 */
class Dom2XmlUtils {
    companion object {
        /**
         * 定义的是 map 的矩形
         */
        public  var MAP_RECTF  =  RectF()
        /**
         * xml path 节点
         */
        private val PATH_TAG = "path"
        /**
         * 定义一个所有身份的集合
         */
        public val mapDataLists = ArrayList<MapData>()

        /**
         * 开始解析 xml
         */
        public fun dom2xml(stream: InputStream?): MutableList<MapData> {
            mapDataLists.clear()
            //dom
            val newInstance = DocumentBuilderFactory.newInstance()
            val newDocumentBuilder = newInstance.newDocumentBuilder()
            //拿到 Docment 对象
            val document = newDocumentBuilder.parse(stream)
            //获取 xml 中属于 path 节点的所有信息
            val elementsByTagName = document.getElementsByTagName(PATH_TAG)

            //定义四个点,确定整个 map 的范围
            var left = -1f
            var right = -1f
            var top = -1f
            var bottom = -1f
            //开始遍历标签，拿到 path 数据组
            for (pathData in 0 until elementsByTagName.length) {
                val item = elementsByTagName.item(pathData) as Element
                val name = item.getAttribute("android:name")
                val fillColor = item.getAttribute("android:fillColor")
                val strokeColor = item.getAttribute("android:strokeColor")
                val strokeWidth = item.getAttribute("android:strokeWidth")
                val pathData = item.getAttribute("android:pathData")
                val path = PathParser.createPathFromPathData(pathData)
                mapDataLists.add(MapData(name, fillColor, strokeColor, strokeWidth, path))
                //获取控件的宽高
                val rect = RectF()
                //获取到每个省份的边界
                path.computeBounds(rect, true)
                //遍历取出每个path中的left取所有的最小值
                left = if (left == -1f) rect.left else Math.min(left, rect.left)
                //遍历取出每个path中的right取所有的最大值
                right = if (right == -1f) rect.right else Math.max(right, rect.right)
                //遍历取出每个path中的top取所有的最小值
                top = if (top == -1f) rect.top else Math.min(top, rect.top)
                //遍历取出每个path中的bottom取所有的最大值
                bottom = if (bottom == -1f) rect.bottom else Math.max(bottom, rect.bottom)
            }
            //赋值给全局
            MAP_RECTF = RectF(left, top, right, bottom)
            return mapDataLists;
        }
    }

}