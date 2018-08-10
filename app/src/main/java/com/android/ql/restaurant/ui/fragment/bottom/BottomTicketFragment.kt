package com.android.ql.restaurant.ui.fragment.bottom

import android.app.Dialog
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.*
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.restaurant.ui.fragment.mine.LoginFragment
import com.android.ql.restaurant.ui.fragment.ticket.SelectTableFragment
import com.android.ql.restaurant.utils.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_bottom_ticket_layout.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class BottomTicketFragment : BaseRecyclerViewFragment<ShopBean>() {

    companion object {
        const val TICKET_SHOP_INFO = "ticket_shop_ticket"
    }


    private val notifyDialog by lazy {
        Dialog(mContext)
    }

    private val postTicketSubscription by lazy {
        RxBus.getDefault().toObservable(PostTicketBean::class.java).subscribe {
            //接收到通知，重新請求前方數據接口
            mPresent.getDataByPost(0x1, RequestParamsHelper.getQueueParams())
        }
    }


    private var currentItem: ShopBean? = null

    override fun createAdapter() = object : BaseQuickAdapter<ShopBean, BaseViewHolder>(R.layout.adapter_order_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: ShopBean?) {
            val iv = helper!!.getView<ImageView>(R.id.mIvOrderItemPic)
            iv.setColorFilter(Color.parseColor("#33000000"))
            GlideManager.loadImage(mContext, item!!.shop_pic, iv)
            helper.setText(R.id.mTvOrderItemName, item.shop_name)
        }
    }

    override fun getLayoutId() = R.layout.fragment_bottom_ticket_layout

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
        postTicketSubscription
        (mTlTicketTitle.layoutParams as ViewGroup.MarginLayoutParams).topMargin = statusBarHeight
        mSwipeRefreshLayout.setBackgroundColor(Color.parseColor("#F6F0F1"))
        mPresent.getDataByPost(0x2, RequestParamsHelper.getVersionUpdate())
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getShopListParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getShopListParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        when (requestID) {
            0x0 -> {
                processList(result as String, ShopBean::class.java)
            }
            0x1 -> {
                handleSuccess(requestID, result)
            }
            0x2 -> {//版本更新
                handleSuccess(requestID, result)
            }
        }
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        super.onHandleSuccess(requestID, obj)
        when (requestID) {
            0x1 -> {
                if (obj != null && obj is JSONObject) {
                    val dataJson = obj.optJSONObject(RESULT_OBJECT)
                    UserInfo.getInstance().ticketBean.ticket_shop = dataJson.optString("ticket_shop")
                    UserInfo.getInstance().ticketBean.ticket_dates = dataJson.optString("ticket_dates")
                    UserInfo.getInstance().ticketBean.ticket_table = dataJson.optString("ticket_table")
                    UserInfo.getInstance().ticketBean.ticket_id = dataJson.optLong("ticket_id")
                    UserInfo.getInstance().ticketBean.ticket_letter = dataJson.optString("ticket_letter")
                    UserInfo.getInstance().ticketBean.ticket_count = dataJson.optInt("ticket_count")

                    if (UserInfo.getInstance().ticketBean!=null &&
                            UserInfo.getInstance().ticketBean.ticket_letter!=null &&
                            UserInfo.getInstance().ticketBean.ticket_count != 0){
                        val contentView = View.inflate(mContext, R.layout.dialog_notify_layout, null)
                        val tv_num = contentView.findViewById<TextView>(R.id.mTvNotifyDialogNum)
                        tv_num.setDiffColorText("您的號碼", "${UserInfo.getInstance().ticketBean.ticket_letter}", color2 = "#880015")
                        contentView.findViewById<TextView>(R.id.mTvNotifyDialogFont).text = "前方還有${UserInfo.getInstance().ticketBean.ticket_count}桌"
                        contentView.findViewById<Button>(R.id.mBtNotifyDialogSubmit).setOnClickListener {
                            notifyDialog.dismiss()
                        }
                        notifyDialog.setContentView(contentView)
                        notifyDialog.show()
                    }
                }
            }
            0x2 -> {
                try {
                    if (obj!=null && obj is JSONObject){
                        val dataJson = obj.optJSONObject(RESULT_OBJECT)
                        val versionCode = dataJson.optString("appApkVer")
                        if (versionCode.toInt() > VersionHelp.currentVersionCode(mContext)) {
                            VersionInfo.getInstance().versionCode = versionCode.toInt()
                            VersionInfo.getInstance().content = dataJson.optString("appApkIntro")
                            VersionInfo.getInstance().downUrl = dataJson.optString("appApk")
                            alert("发现新版本", VersionInfo.getInstance().content, "立即下载", "暂不下载", { _, _ ->
                                toast("正在下载……")
                                VersionHelp.downNewVersion(mContext, Uri.parse(VersionInfo.getInstance().downUrl), "${System.currentTimeMillis()}")
                            }, null)
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }
    }


    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        currentItem = mArrayList[position]
        if (UserInfo.getInstance().isLogin) {
            SelectTableFragment.startSelectTable(mContext, currentItem!!.shop_id)
        } else {
            UserInfo.loginToken = TICKET_SHOP_INFO
            LoginFragment.startLogin(mContext)
        }
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        if (UserInfo.getInstance().isLogin && UserInfo.loginToken == TICKET_SHOP_INFO) {
            SelectTableFragment.startSelectTable(mContext, currentItem!!.shop_id)
        }
    }


    override fun onStop() {
        super.onStop()
        if (notifyDialog.isShowing) {
            notifyDialog.dismiss()
        }
    }

}