package com.android.ql.restaurant.ui.fragment.mine

import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MineContactFragment :BaseRecyclerViewFragment<String>(){

    override fun createAdapter() = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_mine_cantact_item_layout,mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {

        }
    }


    override fun initView(view: View?) {
        super.initView(view)
        mBaseAdapter.addHeaderView(View.inflate(mContext,R.layout.layout_contact_header_layout,null))
        setRefreshEnable(false)
        setLoadEnable(false)
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }



}