package com.android.ql.restaurant.utils

import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.UserInfo
import com.android.ql.restaurant.ui.fragment.mine.LoginFragment
import java.util.regex.Pattern

/**
 * Created by lf on 18.2.10.
 * @author lf on 18.2.10
 */

val PHONE_REG = "^(5|6|8|9)\\d{7}\$"
val IDCARD_REG = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)\$"

/**
 * 显示SnackBar
 */
fun View.showSnackBar(message: String) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    snackBar.view.setBackgroundResource(R.color.colorPrimary)
    snackBar.show()
}

/**
 * 校验输入框是否为空
 */
fun EditText.isEmpty(): Boolean {
    return TextUtils.isEmpty(this.text.toString().trim())
}

fun EditText.isPhone(): Boolean {
    return Pattern.compile(PHONE_REG).matcher(this.text).matches()
}


fun String.isPhone(): Boolean {
    return Pattern.compile(PHONE_REG).matcher(this).matches()
}

fun EditText.isIdCard(): Boolean {
    return Pattern.compile(IDCARD_REG).matcher(this.text).matches()
}

fun String.isIdCard(): Boolean {
    return Pattern.compile(IDCARD_REG).matcher(this).matches()
}

fun EditText.getTextString(): String {
    return this.text.toString().trim()
}

fun EditText.setFirstPoint() {
    if (this.text.startsWith(".")) {
        setText("0.")
        this.setSelection(this.text.toString().length)
    }
}


fun EditText.setTextChangedListener(afterTextWatcher:(Editable?)->Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextWatcher(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}


fun EditText.getFormateFloat():String?{
    try {
        if (this.isEmpty()){
            return null
        }
        return java.lang.Float.parseFloat(getTextString()).toString()
    } catch (e: Exception) {
        return null
    }
}

fun TextView.setDiffColorText(source1: String, source2: String, color1: String = "#444444", color2: String = "#a4a4a4") {
    text = Html.fromHtml(source1.fromHtml(color1) + source2.fromHtml(color2))
}


fun String.fromHtml(color: String = "#c8c9ca"): String {
    return "<font color='$color'>$this</font>"
}

fun View.doClickWithUserStatusStart(token: String, action: (view: View) -> Unit) {
    setOnClickListener {
        if (UserInfo.getInstance().isLogin) {
            action(this)
        } else {
            UserInfo.loginToken = token
            LoginFragment.startLogin(this.context)
        }
    }
}

fun View.doClickWithUseStatusEnd() {
    performClick()
    UserInfo.resetLoginSuccessDoActionToken()
}

