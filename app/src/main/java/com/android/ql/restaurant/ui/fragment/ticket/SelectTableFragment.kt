package com.android.ql.restaurant.ui.fragment.ticket

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.TableBean
import com.android.ql.restaurant.ui.activity.FragmentContainerActivity
import com.android.ql.restaurant.ui.fragment.base.BaseRecyclerViewFragment
import com.android.ql.restaurant.utils.fromHtml
import com.android.ql.restaurant.utils.setDiffColorText
import com.android.ql.restaurant.utils.startPhone
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class SelectTableFragment : BaseRecyclerViewFragment<TableBean>() {


    private var currentItem:TableBean? = null

    override fun createAdapter() = object : BaseQuickAdapter<TableBean, BaseViewHolder>(R.layout.adapter_select_table_item_layout, mArrayList) {
        override fun convert(helper: BaseViewHolder?, item: TableBean?) {
            val tv_name = helper!!.getView<TextView>(R.id.mTvSelectTableItemName)
            tv_name.setDiffColorText("小桌", " 1-2 人")
            val tv_num = helper.getView<TextView>(R.id.mTvSelectTableItemFrontNum)
            tv_num.text = Html.fromHtml("前方 ${"1".fromHtml("#880015")} 桌")
            val container = helper.getView<LinearLayout>(R.id.mLlSelectTableItemContainer)
//            if (item!!.isSelected) {
//                container.setBackgroundResource(R.drawable.shape_ll_5)
//            } else {
//                container.setBackgroundResource(R.drawable.shape_ll_1)
//            }
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        mSwipeRefreshLayout.setBackgroundColor(Color.WHITE)
        val footView = View.inflate(mContext, R.layout.layout_select_table_foot_view, null)
        footView.findViewById<Button>(R.id.mBtSelectTableNext).setOnClickListener {
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("餐廳取票").setClazz(SelectNumAndTimeFragment::class.java).start()
        }
        footView.findViewById<TextView>(R.id.mTvBottomFootTel).setOnClickListener {
            startPhone((it as TextView).text.toString())
        }
        mBaseAdapter.addFooterView(footView)
        setLoadEnable(false)
        setRefreshEnable(false)
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd()
    }

    private fun testAdd() {
        for (i in 0..3) {
            mArrayList.add(TableBean())
        }
        mBaseAdapter.setNewData(mArrayList)
        onRequestEnd(-1)
        mBaseAdapter.loadMoreEnd()
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ColorDrawable())
        return itemDecoration
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
//        currentItem = mArrayList[position]
//        mArrayList.forEach { it.isSelected = it == currentItem }
//        mBaseAdapter.notifyDataSetChanged()
    }

}