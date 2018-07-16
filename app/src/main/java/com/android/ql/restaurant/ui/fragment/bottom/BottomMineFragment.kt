package com.android.ql.restaurant.ui.fragment.bottom

import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseFragment
import com.android.ql.restaurant.ui.fragment.mine.LoginFragment
import com.android.ql.restaurant.ui.fragment.mine.MineContactFragment
import com.android.ql.restaurant.ui.fragment.mine.MineOrderListFragment
import com.android.ql.restaurant.ui.fragment.mine.MinePersonalInfoFragment
import com.android.ql.restaurant.utils.setDiffColorText
import kotlinx.android.synthetic.main.fragment_mine_layout.*

class BottomMineFragment : BaseFragment() {

    private val notifyDialog by lazy {
        Dialog(mContext)
    }

    override fun getLayoutId() = R.layout.fragment_mine_layout

    override fun initView(view: View?) {
        (mTvMineTitle.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight

        mRlPersonalInfoFaceContainer.setOnClickListener{
            LoginFragment.startLogin(mContext)
        }

        mTvMinePersonalInfo.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("個人信息").setClazz(MinePersonalInfoFragment::class.java).start()
        }

        mTvMineOrder.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("我的取票").setClazz(MineOrderListFragment::class.java).start()
        }

        mTvMineCallRes.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("聯繫方式").setClazz(MineContactFragment::class.java).start()
        }

        val contentView = View.inflate(mContext,R.layout.dialog_notify_layout,null)
        val tv_num = contentView.findViewById<TextView>(R.id.mTvNotifyDialogNum)
        tv_num.setDiffColorText("您的號碼","A10",color2 = "#880015")
        contentView.findViewById<Button>(R.id.mBtNotifyDialogSubmit).setOnClickListener {
            notifyDialog.dismiss()
        }
        notifyDialog.setContentView(contentView)
        notifyDialog.show()
    }
}