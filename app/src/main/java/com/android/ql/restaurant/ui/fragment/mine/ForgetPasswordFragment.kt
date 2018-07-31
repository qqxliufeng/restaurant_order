package com.android.ql.restaurant.ui.fragment.mine

import android.graphics.Color
import android.graphics.PorterDuff
import android.text.TextUtils
import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.android.ql.restaurant.utils.getTextString
import com.android.ql.restaurant.utils.isEmpty
import com.android.ql.restaurant.utils.isPhone
import kotlinx.android.synthetic.main.fragment_forget_password_layout.*
import org.jetbrains.anko.support.v4.toast

class ForgetPasswordFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_forget_password_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mTvForgetPasswordBackLogin.setOnClickListener { finish() }
        mBtForgetPassword.setOnClickListener {
            if (mEtForgetPasswordPhone.isEmpty()) {
                toast("請輸入手機號")
                return@setOnClickListener
            }
            if (!mEtForgetPasswordPhone.isPhone()) {
                toast("請輸入正確的手機號")
                return@setOnClickListener
            }
            if (mEtForgetPasswordPassword.isEmpty()) {
                toast("請輸入密碼")
                return@setOnClickListener
            }
            if (mEtForgetPasswordPassword.getTextString().length < 6) {
                toast("密碼長度至少6位")
                return@setOnClickListener
            }
            if (mEtForgetPasswordConfirmPassword.isEmpty()) {
                toast("請再次輸入密碼")
                return@setOnClickListener
            }
            if (mEtForgetPasswordConfirmPassword.isEmpty()) {
                toast("密碼長度至少6位")
                return@setOnClickListener
            }
            if (mEtForgetPasswordConfirmPassword.getTextString() != mEtForgetPasswordPassword.getTextString()) {
                toast("兩次密碼不一致")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x0, RequestParamsHelper.getForgetPWParams(mEtForgetPasswordPhone.getTextString(), mEtForgetPasswordPassword.getTextString(), mEtForgetPasswordConfirmPassword.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在修改密碼~")
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
            toast(e.message!!)
        } else {
            toast("修改失敗~")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        handleSuccess(requestID, result)
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        if (obj != null) {
            toast("修改成功，請牢記密碼！")
            finish()
        }else{
            toast("修改失敗~")
        }
    }
}