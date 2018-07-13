package com.android.ql.restaurant.ui.fragment.bottom

import android.view.View
import android.view.ViewGroup
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine_layout.*

class BottomMineFragment :BaseFragment(){

    override fun getLayoutId() = R.layout.fragment_mine_layout

    override fun initView(view: View?) {
        (mTvMineTitle.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
    }
}