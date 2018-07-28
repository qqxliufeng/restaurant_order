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
import kotlinx.android.synthetic.main.fragment_register_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class RegisterFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_register_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mTvRegisterBackLogin.setOnClickListener {
            finish()
        }
        mBtRegister.setOnClickListener {
            if (mEtRegisterPhone.isEmpty()) {
                toast("請輸入手機號")
                return@setOnClickListener
            }
            if (!mEtRegisterPhone.isPhone()) {
                toast("請輸入正確的手機號")
                return@setOnClickListener
            }
            if (mEtRegisterPassword.isEmpty()) {
                toast("請輸入密碼")
                return@setOnClickListener
            }
            if (mEtRegisterPassword.getTextString().length < 6) {
                toast("密碼長度至少6位")
                return@setOnClickListener
            }
            if (mEtRegisterConfirmPassword.isEmpty()) {
                toast("請再次輸入密碼")
                return@setOnClickListener
            }
            if (mEtRegisterConfirmPassword.isEmpty()) {
                toast("密碼長度至少6位")
                return@setOnClickListener
            }
            if (mEtRegisterConfirmPassword.getTextString() != mEtRegisterPassword.getTextString()) {
                toast("兩次密碼不一致")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x0, RequestParamsHelper.getRegisterParams(mEtRegisterPhone.getTextString(), mEtRegisterPassword.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在註冊~")
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
            toast(e.message!!)
        } else {
            toast("註冊失敗~")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        handleSuccess(requestID, result)
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        if (obj != null) {
            toast("恭喜，註冊成功，請登錄！")
            finish()
        }else{
            toast("註冊失敗~")
        }
    }
}