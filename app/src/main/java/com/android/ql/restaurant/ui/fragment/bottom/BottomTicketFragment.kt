package com.android.ql.restaurant.ui.fragment.bottom

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.restaurant.ui.fragment.ticket.SelectTableFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_bottom_ticket_layout.*

class BottomTicketFragment : BaseRecyclerViewFragment<String>() {

    override fun createAdapter() = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_order_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun getLayoutId() = R.layout.fragment_bottom_ticket_layout

    override fun initView(view: View?) {
        super.initView(view)
        (mTlTicketTitle.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mSwipeRefreshLayout.setBackgroundColor(Color.parseColor("#F6F0F1"))
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }


    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setClazz(SelectTableFragment::class.java).setTitle("餐廳取票").start()
    }

}