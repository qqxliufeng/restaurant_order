package com.android.ql.restaurant.ui.fragment.ticket

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import com.android.ql.restaurant.R
import com.google.android.flexbox.FlexboxLayout
import org.jetbrains.anko.collections.forEachWithIndex

class SelectNumDialogFragment : AppCompatDialogFragment() {


    private val personNumList by lazy {
        val tempList = arrayListOf<SelectPersonNumBean>()
        (1..10).forEach {
            tempList.add(SelectPersonNumBean(it == 1, "$it"))
        }
        tempList.add(SelectPersonNumBean(false, "10人以上"))
        tempList
    }

    private var listener: ((SelectPersonNumBean) -> Unit)? = null

    private var currentSelectBean: SelectPersonNumBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        if (currentSelectBean == null) {
            personNumList.forEachWithIndex { index, it ->
                it.isChecked = index == 0
                if (it.isChecked) currentSelectBean = it
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.dialog_select_num_layout, container)
        val flexboxLayout = contentView.findViewById<FlexboxLayout>(R.id.mFlSelectNumContainer)
        personNumList.forEachWithIndex { index, item ->
            View.inflate(context, R.layout.layout_select_num_ctv, flexboxLayout) as FlexboxLayout
            val checkedTextView = flexboxLayout.getChildAt(index) as CheckedTextView
            checkedTextView.text = item.num
            checkedTextView.isChecked = item.isChecked
            checkedTextView.setOnClickListener {
                personNumList.forEachWithIndex { i, it ->
                    it.isChecked = it == item
                    (flexboxLayout.getChildAt(i) as CheckedTextView).isChecked = it.isChecked
                }
            }
        }
        contentView.findViewById<TextView>(R.id.mTvSelectNumDialogCancel).setOnClickListener {
            personNumList.forEach { it.isChecked = it == currentSelectBean }
            dismiss()
        }
        contentView.findViewById<TextView>(R.id.mTvSelectNumDialogSubmit).setOnClickListener {
            currentSelectBean = personNumList.filter { it.isChecked }[0]
            currentSelectBean?.isChecked = true
            listener?.invoke(currentSelectBean!!)
            dismiss()
        }
        return contentView
    }

    fun myShow(manager: FragmentManager?, tag: String?, listener: (SelectPersonNumBean) -> Unit) {
        this.listener = listener
        super.show(manager, tag)
    }


    class SelectPersonNumBean(var isChecked: Boolean, var num: String)


}