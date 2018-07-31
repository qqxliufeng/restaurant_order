package com.android.ql.restaurant.ui.fragment.ticket

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.TableBean
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.restaurant.utils.RequestParamsHelper
import com.android.ql.restaurant.utils.fromHtml
import com.android.ql.restaurant.utils.setDiffColorText
import com.android.ql.restaurant.utils.startPhone
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

class SelectTableFragment : BaseRecyclerViewFragment<TableBean>() {

    companion object {
        fun startSelectTable(context: Context, shopId: String) {
            FragmentContainerActivity.from(context).setExtraBundle(bundleOf(Pair("shop_id", shopId))).setNeedNetWorking(true).setClazz(SelectTableFragment::class.java).setTitle("餐廳取票").start()
        }
    }


    private val shopId by lazy {
        if (arguments != null) {
            arguments!!.getString("shop_id")
        } else {
            ""
        }
    }

    override fun createAdapter() = object : BaseQuickAdapter<TableBean, BaseViewHolder>(R.layout.adapter_select_table_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: TableBean?) {
            val tv_name = helper!!.getView<TextView>(R.id.mTvSelectTableItemName)
            tv_name.setDiffColorText(item!!.table_name, " ${item.table_intro}")
            val tv_num = helper.getView<TextView>(R.id.mTvSelectTableItemFrontNum)
            tv_num.text = Html.fromHtml("前方 ${item.table_count.fromHtml("#880015")} 桌")
        }
    }

    override fun getEmptyMessage() = "暫無桌數~~"

    override fun initView(view: View?) {
        super.initView(view)
        mSwipeRefreshLayout.setBackgroundColor(Color.WHITE)
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.getTableListParam(shopId))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, TableBean::class.java)
        val jsonObject = JSONObject(result)
        val shopJson = jsonObject.optJSONObject("shop")
        if (shopJson != null) {
            val footView = View.inflate(mContext, R.layout.layout_select_table_foot_view, null)
            footView.findViewById<Button>(R.id.mBtSelectTableNext).setOnClickListener {
                SelectNumAndTimeFragment.startSelectNumAndTime(mContext, shopJson.optString("shop_phone"), shopJson.optString("shop_dizhi"), "${mArrayList[0].table_shop}")
            }
            val tv_phone = footView.findViewById<TextView>(R.id.mTvBottomFootTel)
            val tv_address = footView.findViewById<TextView>(R.id.mTvBottomFootAddress)
            tv_phone?.setOnClickListener {
                startPhone((it as TextView).text.toString())
            }
            mBaseAdapter.addFooterView(footView)
            tv_phone?.text = "TEL：${shopJson.optString("shop_phone")}"
            tv_address?.text = "地址：${shopJson.optString("shop_dizhi")}"
        }
        setLoadEnable(false)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ColorDrawable())
        return itemDecoration
    }
}