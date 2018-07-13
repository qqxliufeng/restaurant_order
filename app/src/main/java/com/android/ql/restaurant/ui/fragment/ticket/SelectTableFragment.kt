package com.android.ql.restaurant.ui.fragment.ticket

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class SelectTableFragment :BaseRecyclerViewFragment<String>(){


    override fun createAdapter() = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_select_table_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {

        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        mSwipeRefreshLayout.setBackgroundColor(Color.WHITE)
        val footView = View.inflate(mContext,R.layout.layout_select_table_foot_view,null)
        footView.findViewById<Button>(R.id.mBtSelectTableNext).setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("餐廳取票").setClazz(SelectNumAndTimeFragment::class.java).start()
        }
        mBaseAdapter.addFooterView(footView)
        setLoadEnable(false)
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration =  super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ColorDrawable())
        return itemDecoration
    }

}