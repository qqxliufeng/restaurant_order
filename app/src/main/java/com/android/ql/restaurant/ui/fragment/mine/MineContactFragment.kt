package com.android.ql.restaurant.ui.fragment.mine

import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.ShopBean
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MineContactFragment : BaseRecyclerViewFragment<ShopBean>() {

    override fun createAdapter() = object : BaseQuickAdapter<ShopBean, BaseViewHolder>(R.layout.adapter_mine_cantact_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: ShopBean?) {
            helper!!.setText(R.id.mTvMineContactItemPhone, "TEL：${item!!.shop_phone}")
            helper.setText(R.id.mTvMineContactItemAddress, "地址：${item.shop_dizhi}")
            helper.setText(R.id.mTvMineContactItemName, item.shop_name)
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        mBaseAdapter.addHeaderView(View.inflate(mContext, R.layout.layout_contact_header_layout, null))
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getContactParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, ShopBean::class.java)
    }

}