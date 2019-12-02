package com.devyk.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.devyk.customview.adapter.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        recyclerView.layoutManager =  LinearLayoutManager(this)
        recyclerView2.layoutManager =  LinearLayoutManager(this)
        recyclerView3.layoutManager =  LinearLayoutManager(this)

        recyclerView.adapter = RecyclerViewAdapter(getListData("页面一"))
        recyclerView2.adapter = RecyclerViewAdapter(getListData("页面二"))
        recyclerView3.adapter = RecyclerViewAdapter(getListData("页面三"))

        val width = recyclerView.width;
        val height = recyclerView.height;

        println("recyclerView:$width + $height")


        recyclerView.post {
            val width = recyclerView.width;
            val height = recyclerView.height;

            println("recyclerView post :$width + $height")
        }
    }

    private fun getListData(i: String): List< String> {
        var list =  mutableListOf(i)

        for (index in 1 .. 100){
            list.add("hello:$index")
        }
        return list

    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN)
            println("事件分发机制开始分发 ----> Activity  dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN)
            println("事件分发机制处理 ----> Activity onTouchEvent 执行")
        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
        val width = recyclerView.width;
        val height = recyclerView.height;

        println("recyclerView:$width + $height")
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val width = recyclerView.width;
        val height = recyclerView.height;

        println("recyclerView:$width + $height")
    }
}
