package com.devyk.custom_view.flow

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.devyk.custom_view.R


/**
 * Created by zhy on 15/9/10.
 */
public class TagFlowLayout :
    FlowLayout, TagAdapter.OnDataChangedListener {



    private lateinit var mTagAdapter: TagAdapter<String>

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {init(context,attrs)}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(context,attrs)}


    public fun setAdapter(adapter: TagAdapter<String>) {
        this.mTagAdapter = adapter
        this.mTagAdapter.setOnDataChangedListener(this)
        mSelectedView.clear()
        changeAdapter()

    }


    private var mSelectedMax = -1//-1为不限制数量

    private val mSelectedView = HashSet<Int>()

    private var mOnSelectListener: OnSelectListener? = null
    private var mOnTagClickListener: OnTagClickListener? = null

    val selectedList: Set<Int>
        get() = HashSet(mSelectedView)

    interface OnSelectListener {
        fun onSelected(selectPosSet: Set<Int>)
    }

    interface OnTagClickListener {
        fun onTagClick(view: View, position: Int, parent: FlowLayout): Boolean
    }

   fun init(context: Context,attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)
        mSelectedMax = ta.getInt(R.styleable.FlowLayout_max_select, -1)
        ta.recycle()
    }


    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val cCount = getChildCount()
        for (i in 0 until cCount) {
            val tagView = getChildAt(i) as TagView
            if (tagView.visibility == View.GONE) {
                continue
            }
            if (tagView.tagView.visibility == View.GONE) {
                tagView.visibility = View.GONE
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    fun setOnSelectListener(onSelectListener: OnSelectListener) {
        mOnSelectListener = onSelectListener
    }


    fun setOnTagClickListener(onTagClickListener: OnTagClickListener) {
        mOnTagClickListener = onTagClickListener
    }

    private fun changeAdapter() {
        removeAllViews()
        val adapter = this.mTagAdapter
        var tagViewContainer: TagView? = null
        val preCheckedList = this.mTagAdapter!!.preCheckedList
        for (i in 0 until adapter.count) {
            val tagView = adapter.getView(this, i, adapter.getItem(i))

            tagViewContainer = TagView(getContext())
            tagView.isDuplicateParentStateEnabled = true
            if (tagView.layoutParams != null) {
                tagViewContainer.layoutParams = tagView.layoutParams


            } else {
                val lp = MarginLayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(
                    dip2px(getContext(), 5f),
                    dip2px(getContext(), 5f),
                    dip2px(getContext(), 5f),
                    dip2px(getContext(), 5f)
                )
                tagViewContainer.layoutParams = lp
            }
            val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            tagView.layoutParams = lp
            tagViewContainer.addView(tagView)
            addView(tagViewContainer)

            if (preCheckedList.contains(i)) {
                setChildChecked(i, tagViewContainer)
            }

        }
        mSelectedView.addAll(preCheckedList)
    }



    private fun setChildChecked(position: Int, view: TagView) {
        view.isChecked = true
        mTagAdapter!!.onSelected(position, view.tagView)
    }






    protected override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState())

        var selectPos = ""
        if (mSelectedView.size > 0) {
            for (key in mSelectedView) {
                selectPos += "$key|"
            }
            selectPos = selectPos.substring(0, selectPos.length - 1)
        }
        bundle.putString(KEY_CHOOSE_POS, selectPos)
        return bundle
    }

    protected override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            val mSelectPos = state.getString(KEY_CHOOSE_POS)
            if (!TextUtils.isEmpty(mSelectPos)) {
                val split = mSelectPos!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (pos in split) {
                    val index = Integer.parseInt(pos)
                    mSelectedView.add(index)

                    val tagView = getChildAt(index) as TagView
                    if (tagView != null) {
                        setChildChecked(index, tagView)
                    }
                }

            }
            super.onRestoreInstanceState(state.getParcelable(KEY_DEFAULT))
            return
        }
        super.onRestoreInstanceState(state)
    }


    override fun onChanged() {
        mSelectedView.clear()
        changeAdapter()
    }


    companion object {
        private val TAG = "TagFlowLayout"


        private val KEY_CHOOSE_POS = "key_choose_pos"
        private val KEY_DEFAULT = "key_default"

        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}
