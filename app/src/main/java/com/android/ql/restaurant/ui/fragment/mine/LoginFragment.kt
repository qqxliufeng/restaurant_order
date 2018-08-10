package com.android.ql.restaurant.ui.fragment.mine

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.TextUtils
import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.present.UserPresent
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.android.ql.restaurant.utils.getTextString
import com.android.ql.restaurant.utils.isEmpty
import com.android.ql.restaurant.utils.isPhone
import kotlinx.android.synthetic.main.fragment_login_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class LoginFragment : BaseNetWorkingFragment() {

    companion object {
        fun startLogin(context: Context) {
            FragmentContainerActivity.from(context).setClazz(LoginFragment::class.java).setTitle("登录").setNeedNetWorking(true).start()
        }
    }

    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_login_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)

        mTvLoginForgetPassword.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(ForgetPasswordFragment::class.java).setTitle(mTvLoginForgetPassword.text.toString()).setNeedNetWorking(true).start()
        }
        mTvLoginRegister.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(RegisterFragment::class.java).setTitle(mTvLoginRegister.text.toString()).setNeedNetWorking(true).start()
        }
        mBtLogin.setOnClickListener {
            if (mEtLoginPhone.isEmpty()) {
                toast("請輸入手機號")
                return@setOnClickListener
            }
            if (!mEtLoginPhone.isPhone()) {
                toast("請輸入正確的手機號")
                return@setOnClickListener
            }
            if (mEtLoginPassword.isEmpty()) {
                toast("請輸入密碼")
                return@setOnClickListener
            }
            if (mEtLoginPassword.getTextString().length < 6) {
                toast("密碼長度至少6位")
                return@setOnClickListener
            }
            mPresent.getDataByPost(0x0,RequestParamsHelper.getLoginParams(mEtLoginPhone.getTextString(),mEtLoginPassword.getTextString()))
        }
    }


    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在登錄……")
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
            toast(e.message!!)
        }else{
            toast("登錄失敗~~")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        handleSuccess(requestID,result)
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        if (obj!=null && obj is JSONObject) {
            userPresent.onLogin(obj)
            finish()
        }
    }

}