package com.android.ql.restaurant.ui.fragment.ticket

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_select_num_and_time_layout.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

class SelectNumAndTimeFragment :BaseNetWorkingFragment(){

    private val selectNumDialogFragment by lazy {
        SelectNumDialogFragment()
    }

    private val dateDialog by lazy {
        DatePickerDialog(mContext,{view,year,month,dayOfMonth->
            date = "$year-${month+1}-$dayOfMonth"
            timeDialog.show()

        },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
    }


    private val timeDialog by lazy {
        TimePickerDialog(mContext,{view,hourOfDay,minute->
            time = "$hourOfDay:$minute"
            mTvSelectTimeText.text = "$date  $time"
        },Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true)
    }


    private var date:String? = null
    private var time:String? = null
    private var num:String? = null

    override fun getLayoutId() = R.layout.fragment_select_num_and_time_layout


    override fun initView(view: View?) {
        mRlSelectTicketTime.setOnClickListener {
            dateDialog.show()
        }

        mRlSelectTicketNum.setOnClickListener {
            selectNumDialogFragment.myShow(childFragmentManager,"select_num_dialog"){
                num = it.num
                mTvSelectNumText.text = "${it.num}人"
            }
        }

        mBtSelectTableNext.setOnClickListener {
            if (date == null || time == null){
                toast("請先選擇預計時間")
                return@setOnClickListener
            }
            if (num == null){
                toast("請先選擇預計人數")
                return@setOnClickListener
            }
            FragmentContainerActivity.from(mContext).setClazz(SelectTicketResultFragment::class.java).setTitle("餐廳取票").setNeedNetWorking(false).start()
        }
    }
}