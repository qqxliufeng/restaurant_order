package com.android.ql.restaurant.ui.fragment.mine

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.TicketBean
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.android.ql.restaurant.utils.alert
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.support.v4.toast

class MineOrderListFragment : BaseRecyclerViewFragment<TicketBean>() {

    override fun createAdapter() = object : BaseQuickAdapter<TicketBean, BaseViewHolder>(R.layout.adapter_mine_order_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: TicketBean?) {
            helper!!.setText(R.id.mTvMineTicketItemName, item!!.ticket_shop_name)
            helper.setText(R.id.mTvMineTicketItemCount, "預計用餐人數 ${item.ticket_number} 人")
            helper.setText(R.id.mTvMineTicketItemTime, "預計用餐時間：${item.ticket_dates}")
            helper.setText(R.id.mTvMineTicketItemNum, item.ticket_letter)
            val tv_submit = helper.getView<TextView>(R.id.mTvMineTicketItemSubmit)
            val tv_cancel = helper.getView<TextView>(R.id.mTvMineTicketItemCancel)
            when (item.ticket_is_state) {
                TicketBean.TicketStatus.ORDERING.status -> {//正在排队
                    tv_submit.isEnabled = true
                    tv_cancel.isEnabled = true
                    helper.addOnClickListener(R.id.mTvMineTicketItemSubmit)
                    helper.addOnClickListener(R.id.mTvMineTicketItemCancel)
                }
                TicketBean.TicketStatus.COMPLEMENT.status -> {// 已完成
                    tv_submit.isEnabled = false
                    tv_cancel.isEnabled = false
                }
                TicketBean.TicketStatus.CANCEL.status -> {//已取消
                    tv_submit.isEnabled = false
                    tv_cancel.isEnabled = false
                }
            }
        }
    }

    private var currentBean: TicketBean? = null

    override fun initView(view: View?) {
        super.initView(view)
        mBaseAdapter.setHeaderFooterEmpty(false, true)
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getTicketListParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getTicketListParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        when (requestID) {
            0x0 -> processList(result as String, TicketBean::class.java)
            0x1, 0x2 -> {
                handleSuccess(requestID, result)
            }
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.shape_mine_order)!!)
        return itemDecoration
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        currentBean = mArrayList[position]
        when (view!!.id) {
            R.id.mTvMineTicketItemSubmit -> {
                alert("是否已到店？", "是", "否") { _, _ ->
                    mPresent.getDataByPost(0x1, RequestParamsHelper.getTicketUpParam(currentBean!!.ticket_id.toString(), "2"))
                }
            }
            R.id.mTvMineTicketItemCancel -> {
                alert("確定要取消排隊嗎？", "取消", "不取消") { _, _ ->
                    mPresent.getDataByPost(0x2, RequestParamsHelper.getTicketUpParam(currentBean!!.ticket_id.toString(), "3"))
                }
            }
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x1 -> {
                getFastProgressDialog("正在加載……")
            }
            0x2 -> {
                getFastProgressDialog("正在取消排隊……")
            }
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        when (requestID) {
            0x2 -> {
                if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
                    toast(e.message!!)
                } else {
                    toast("取消失敗，稍後重試~")
                }
            }
            0x1 -> {
                if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
                    toast(e.message!!)
                } else {
                    toast("操作失敗，稍後重試~")
                }
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        when (requestID) {
            0x2 -> {
                toast("取消成功~")
                currentBean?.ticket_is_state = TicketBean.TicketStatus.CANCEL.status
                mBaseAdapter.notifyItemChanged(mArrayList.indexOf(currentBean))
            }
            0x1 -> {
                toast("操作成功~")
                currentBean?.ticket_is_state = TicketBean.TicketStatus.COMPLEMENT.status
                mBaseAdapter.notifyItemChanged(mArrayList.indexOf(currentBean))
            }
        }
    }

}