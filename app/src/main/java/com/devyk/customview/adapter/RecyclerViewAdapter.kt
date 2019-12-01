package com.devyk.customview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devyk.customview.R

/**
 * <pre>
 *     author  : devyk on 2019-11-20 23:54
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RecyclerViewAdapter
 * </pre>
 */
class RecyclerViewAdapter(list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var listData = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_expandable_list_item_1, null)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {

        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as MyViewHolder
        var data = listData.get(position)

        viewHolder.position = position
        viewHolder.txt?.run {
            setText(data)
        }


    }
}


class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    public var position: Int? = null

    public var txt: TextView? = null


    init {
        txt = itemView.findViewById(android.R.id.text1)
    }

}