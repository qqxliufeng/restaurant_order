package com.android.ql.restaurant.ui.fragment.mine

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_forget_password_layout.*

class ForgetPasswordFragment :BaseNetWorkingFragment(){

    override fun getLayoutId() = R.layout.fragment_forget_password_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mTvForgetPasswordBackLogin.setOnClickListener { finish() }
    }
}