package com.android.ql.restaurant.ui.fragment.ticket

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.UserInfo
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.android.ql.restaurant.utils.alert
import kotlinx.android.synthetic.main.fragment_select_ticket_result_layout.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.toast

class SelectTicketResultFragment : BaseNetWorkingFragment() {

    companion object {
        fun startSelectTicket(context: Context, id: String, num: String, time: String) {
            FragmentContainerActivity
                    .from(context)
                    .setClazz(SelectTicketResultFragment::class.java)
                    .setTitle("餐廳取票")
                    .setExtraBundle(bundleOf(Pair("num", num), Pair("time", time), Pair("id", id)))
                    .setNeedNetWorking(true)
                    .start()
        }
    }

    private var state: String = "2"

    override fun getLayoutId() = R.layout.fragment_select_ticket_result_layout

    override fun initView(view: View?) {
        mTvSelectTicketResultNum.text = "${arguments!!.getString("num")}人就餐"
        mTvSelectTicketResultTime.text = "取票時間：${arguments!!.getString("time")}"
        mTvSelectTicketResultOrderNum.text = UserInfo.getInstance().ticketBean.ticket_letter
        mTvSelectTicketResultOrderCount.text = "前方還有${UserInfo.getInstance().ticketBean.ticket_count}桌"
        mTvSelectTicketResultCancelOrder.setOnClickListener {
            alert("確定要取消排隊嗎？", "取消", "不取消") { _, _ ->
                state = "3"
                mPresent.getDataByPost(0x0, RequestParamsHelper.getTicketUpParam(arguments!!.getString("id"), state))
            }
        }
        mTvSelectTicketResultSubmitOrder.setOnClickListener {
            alert("是否已到店？", "是", "否") { _, _ ->
                state = "2"
                mPresent.getDataByPost(0x1, RequestParamsHelper.getTicketUpParam(arguments!!.getString("id"), state))
            }
        }
    }


    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0) {
            getFastProgressDialog("正在取消排隊……")
        } else {
            getFastProgressDialog("正在加載……")
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        when (requestID) {
            0x0 -> {
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

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        handleSuccess(requestID, result)
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        when (requestID) {
            0x0 -> {
                if (obj != null) {
                    toast("取消成功~")
                    finish()
                }
            }
            0x1 -> {
                if (obj != null) {
                    toast("操作成功~")
                    finish()
                }
            }
        }
    }

}