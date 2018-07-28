package com.android.ql.restaurant.ui.fragment.bottom

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.ShopBean
import com.android.ql.restaurant.data.UserInfo
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.restaurant.ui.fragment.mine.LoginFragment
import com.android.ql.restaurant.ui.fragment.ticket.SelectTableFragment
import com.android.ql.restaurant.utils.GlideManager
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_bottom_ticket_layout.*

class BottomTicketFragment : BaseRecyclerViewFragment<ShopBean>() {

    companion object {
        const val TICKET_SHOP_INFO = "ticket_shop_ticket"
    }

    private var currentItem: ShopBean? = null

    override fun createAdapter() = object : BaseQuickAdapter<ShopBean, BaseViewHolder>(R.layout.adapter_order_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: ShopBean?) {
            val iv = helper!!.getView<ImageView>(R.id.mIvOrderItemPic)
            iv.setColorFilter(Color.parseColor("#33000000"))
            GlideManager.loadImage(mContext, item!!.shop_pic, iv)
            helper.setText(R.id.mTvOrderItemName, item.shop_name)
        }
    }

    override fun getLayoutId() = R.layout.fragment_bottom_ticket_layout

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
        (mTlTicketTitle.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mSwipeRefreshLayout.setBackgroundColor(Color.parseColor("#F6F0F1"))
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getShopListParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getShopListParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, ShopBean::class.java)
    }


    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        currentItem = mArrayList[position]
        if (UserInfo.getInstance().isLogin) {
            SelectTableFragment.startSelectTable(mContext, currentItem!!.shop_id)
        } else {
            UserInfo.loginToken = TICKET_SHOP_INFO
            LoginFragment.startLogin(mContext)
        }
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        if (UserInfo.getInstance().isLogin && UserInfo.loginToken == TICKET_SHOP_INFO) {
            SelectTableFragment.startSelectTable(mContext, currentItem!!.shop_id)
        }
    }

}