package com.android.ql.restaurant.ui.fragment.mine

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MineOrderListFragment :BaseRecyclerViewFragment<String>(){

    override fun createAdapter() = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_mine_order_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }


    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.shape_mine_order)!!)
        return itemDecoration
    }

}