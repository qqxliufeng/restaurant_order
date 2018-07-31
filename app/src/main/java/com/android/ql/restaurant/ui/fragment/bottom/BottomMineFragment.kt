package com.android.ql.restaurant.ui.fragment.bottom

import android.view.View
import android.view.ViewGroup
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.UserInfo
import com.android.ql.restaurant.present.UserPresent
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.restaurant.ui.fragment.mine.LoginFragment
import com.android.ql.restaurant.ui.fragment.mine.MineContactFragment
import com.android.ql.restaurant.ui.fragment.mine.MineOrderListFragment
import com.android.ql.restaurant.ui.fragment.mine.MinePersonalInfoFragment
import com.android.ql.restaurant.utils.*
import kotlinx.android.synthetic.main.fragment_mine_layout.*
import org.json.JSONObject

class BottomMineFragment : BaseNetWorkingFragment() {

//    private val notifyDialog by lazy {
//        Dialog(mContext)
//    }

    companion object {
        const val MINE_USER_INFO_FLAG = "mine_user_info_flag"
        const val MINE_TICKET_FLAG = "mine_ticket_flag"
        const val MINE_CONTACT_FLAG = "mine_contact_flag"
    }


    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_mine_layout

    private val modifyInfoSubscription by lazy {
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (it == "modify info success") {
                GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
                mTvMineNickName.text = UserInfo.getInstance().user_nickname
            }
        }
    }

    override fun initView(view: View?) {
        registerLoginSuccessBus()
        modifyInfoSubscription
        (mTvMineTitle.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight

        if (UserInfo.getInstance().isLogin) {
            GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
            mTvMineNickName.text = UserInfo.getInstance().user_nickname
        }

        mSrfMine.setOnRefreshListener {
            if (UserInfo.isCacheUserId(mContext)) {
                mPresent.getDataByPost(0x0, RequestParamsHelper.getPersonalParam(UserInfo.getUserIdFromCache(mContext)))
            } else {
                mSrfMine.post { mSrfMine.isRefreshing = false }
            }
        }

        mRlPersonalInfoFaceContainer.doClickWithUserStatusStart(MINE_USER_INFO_FLAG) {
            if (UserInfo.getInstance().isLogin) {
                FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("個人信息").setClazz(MinePersonalInfoFragment::class.java).start()
            } else {
                LoginFragment.startLogin(mContext)
            }
        }

        mTvMinePersonalInfo.doClickWithUserStatusStart(MINE_USER_INFO_FLAG) {
            if (UserInfo.getInstance().isLogin) {
                FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("個人信息").setClazz(MinePersonalInfoFragment::class.java).start()
            } else {
                LoginFragment.startLogin(mContext)
            }
        }

        mTvMineOrder.doClickWithUserStatusStart(MINE_TICKET_FLAG) {
            if (UserInfo.getInstance().isLogin) {
                FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("我的取票").setClazz(MineOrderListFragment::class.java).start()
            } else {
                LoginFragment.startLogin(mContext)
            }
        }

        mTvMineCallRes.doClickWithUserStatusStart(MINE_CONTACT_FLAG) {
            if (UserInfo.getInstance().isLogin) {
                FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("聯繫方式").setClazz(MineContactFragment::class.java).start()
            } else {
                LoginFragment.startLogin(mContext)
            }
        }

//        val contentView = View.inflate(mContext,R.layout.dialog_notify_layout,null)
//        val tv_num = contentView.findViewById<TextView>(R.id.mTvNotifyDialogNum)
//        tv_num.setDiffColorText("您的號碼","A10",color2 = "#880015")
//        contentView.findViewById<Button>(R.id.mBtNotifyDialogSubmit).setOnClickListener {
//            notifyDialog.dismiss()
//        }
//        notifyDialog.setContentView(contentView)
//        notifyDialog.show()
    }


    override fun onRequestEnd(requestID: Int) {
        super.onRequestEnd(requestID)
        mSrfMine.post {
            mSrfMine.isRefreshing = false
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        onHandleSuccess(requestID, result)
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        if (obj != null) {
            userPresent.onLoginNoBus(obj as JSONObject)
            GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
            mTvMineNickName.text = UserInfo.getInstance().user_nickname
        }
    }


    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        if (UserInfo.getInstance().isLogin) {
            GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvMineFace)
            mTvMineNickName.text = UserInfo.getInstance().user_nickname
            when (UserInfo.loginToken) {
                MINE_USER_INFO_FLAG -> {
                    mTvMinePersonalInfo.doClickWithUseStatusEnd()
                }
                MINE_TICKET_FLAG -> {
                    mTvMineOrder.doClickWithUseStatusEnd()
                }
                MINE_CONTACT_FLAG -> {
                    mTvMineCallRes.doClickWithUseStatusEnd()
                }
            }
        }
    }

    override fun onDestroyView() {
        unsubscribe(modifyInfoSubscription)
        super.onDestroyView()
    }
}