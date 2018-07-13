package com.android.ql.restaurant.ui.fragment.bottom

import android.view.View
import android.view.ViewGroup
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseFragment
import com.android.ql.restaurant.ui.fragment.mine.MineContactFragment
import com.android.ql.restaurant.ui.fragment.mine.MineOrderListFragment
import com.android.ql.restaurant.ui.fragment.mine.MinePersonalInfoFragment
import kotlinx.android.synthetic.main.fragment_mine_layout.*

class BottomMineFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_mine_layout

    override fun initView(view: View?) {
        (mTvMineTitle.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight

        mTvMinePersonalInfo.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("個人信息").setClazz(MinePersonalInfoFragment::class.java).start()
        }

        mTvMineOrder.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("我的預約").setClazz(MineOrderListFragment::class.java).start()
        }

        mTvMineCallRes.setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("聯繫方式").setClazz(MineContactFragment::class.java).start()
        }
    }
}