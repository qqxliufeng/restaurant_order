package com.android.ql.restaurant.ui.fragment.ticket

import android.app.TimePickerDialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.UserInfo
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.android.ql.restaurant.utils.startPhone
import kotlinx.android.synthetic.main.fragment_select_num_and_time_layout.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SelectNumAndTimeFragment : BaseNetWorkingFragment() {

    companion object {
        fun startSelectNumAndTime(mContext: Context, phone: String, address: String, shopId: String) {
            FragmentContainerActivity
                    .from(mContext)
                    .setNeedNetWorking(true)
                    .setExtraBundle(bundleOf(Pair("phone", phone), Pair("address", address), Pair("shopId", shopId)))
                    .setTitle("餐廳取票")
                    .setClazz(SelectNumAndTimeFragment::class.java)
                    .start()
        }
    }

    private val selectNumDialogFragment by lazy {
        SelectNumDialogFragment()
    }

    private val timeDialog by lazy {
        TimePickerDialog(mContext, { view, hourOfDay, minute ->
            time = "$hourOfDay:$minute"
            mTvSelectTimeText.text = "$date $time"
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true)
    }

    private var time: String? = null
    private var num: String? = null

    private val date by lazy {
        val millis = System.currentTimeMillis()
        val dataFormat = SimpleDateFormat("YYYY-MM-dd")
        dataFormat.format(Date(millis))
    }

    override fun getLayoutId() = R.layout.fragment_select_num_and_time_layout

    override fun initView(view: View?) {
        mTvSelectNumAndTimeTel.text = "TEL：${arguments?.getString("phone")}"
        mTvSelectNumAndTimeAddress.text = "地址：${arguments?.getString("address")}"
        mRlSelectTicketTime.setOnClickListener {
            timeDialog.show()
        }

        mRlSelectTicketNum.setOnClickListener {
            selectNumDialogFragment.myShow(childFragmentManager, "select_num_dialog") {
                if (it.num == "10人以上") {
                    num = "11"
                    mTvSelectNumText.text = it.num
                } else {
                    num = it.num
                    mTvSelectNumText.text = "${it.num}人"
                }
            }
        }

        mBtSelectTableNext.setOnClickListener {
            if (num == null) {
                toast("請先選擇預計用餐人數")
                return@setOnClickListener
            }
            if (time == null) {
                toast("請先選擇預計用餐時間")
                return@setOnClickListener
            }

            mPresent.getDataByPost(0x0, RequestParamsHelper.getTicketParam(
                    arguments!!.getString("shopId", ""),
                    "$num",
                    "$date $time"
            ))
        }

        mTvSelectNumAndTimeTel.setOnClickListener {
            startPhone(mTvSelectNumAndTimeTel.text.toString())
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在取票……")
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
            toast(e.message!!)
        } else {
            toast("取票失敗，請重試~~")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        handleSuccess(requestID, result)
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        if (obj != null && obj is JSONObject) {
            val dataJSONObject = obj.optJSONObject("data")
            UserInfo.getInstance().count = dataJSONObject.optString("count")
            UserInfo.getInstance().number = dataJSONObject.optString("number")
            toast("取票成功~")
            SelectTicketResultFragment.startSelectTicket(mContext, dataJSONObject.optString("ticket_id"), num!!, time!!)
            finish()
        }
    }

}