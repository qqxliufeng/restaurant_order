package com.android.ql.restaurant.ui.fragment.mine

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import com.android.ql.lf.carapp.data.ImageBean
import com.android.ql.restaurant.R
import com.android.ql.restaurant.data.UserInfo
import com.android.ql.restaurant.present.UserPresent
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.restaurant.utils.*
import com.soundcloud.android.crop.Crop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_personal_info_layout.*
import okhttp3.MultipartBody
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.io.File

class MinePersonalInfoFragment : BaseNetWorkingFragment() {

    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_personal_info_layout

    override fun initView(view: View?) {
        mEtPersonalInfoNickName.hint = UserInfo.getInstance().user_nickname
        GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().user_pic, mIvPersonalInfoFace)
        mEtPersonalInfoNickName.setSelection(mEtPersonalInfoNickName.getTextString().lastIndex + 1)
        mRlPersonalInfoFace.setOnClickListener {
            openImageChoose(MimeType.ofImage(), 1)
        }
        mBtPersonalInfoSave.setOnClickListener {
            if (mEtPersonalInfoNickName.isEmpty()) {
                toast("請輸入昵稱")
                return@setOnClickListener
            }
            if (mEtPersonalInfoNickName.getTextString() == UserInfo.getInstance().user_nickname) {
                toast("請輸入與原來不同的昵稱")
                return@setOnClickListener
            }
            mContext.hiddenKeyBoard(mEtPersonalInfoNickName.windowToken)
            mPresent.getDataByPost(0x0, RequestParamsHelper.getUpdateNickNameParams(mEtPersonalInfoNickName.getTextString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0) {
            getFastProgressDialog("正在保存昵稱……")
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        when (requestID) {
            0x0 -> {
                if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
                    toast(e.message!!)
                } else {
                    toast("昵稱保存失敗")
                }
            }
            0x1 -> {
                if (e is NullPointerException && !TextUtils.isEmpty(e.message)) {
                    toast(e.message!!)
                } else {
                    toast("頭像上傳失敗")
                }
            }
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        handleSuccess(requestID,result)
    }

    override fun onHandleSuccess(requestID: Int, obj: Any?) {
        if (requestID == 0x0) {
            if (obj != null && obj is JSONObject) {
                toast("昵稱修改成功")
                val data = obj.optString(RESULT_OBJECT)
                userPresent.modifyInfoForName(data)
            }
        }else{
            if (obj != null && obj is JSONObject) {
                toast("頭像修改成功")
                val data = obj.optString(RESULT_OBJECT)
                userPresent.modifyInfoForPic(data)
                GlideManager.loadFaceCircleImage(mContext, data, mIvPersonalInfoFace)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val resultData = Matisse.obtainResult(data)
                resultData[0].let {
                    val dir = File("${Constants.IMAGE_PATH}face/")
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    val desUri = Uri.fromFile(File(dir, "${System.currentTimeMillis()}.jpg"))
                    Crop.of(it, desUri).start(mContext, this@MinePersonalInfoFragment)
                }
            }
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val uri = Crop.getOutput(data)
                GlideManager.loadCircleImage(mContext, uri.path, mIvPersonalInfoFace)

                ImageUploadHelper(object : ImageUploadHelper.OnImageUploadListener {
                    override fun onActionFailed() {
                        toast("頭像上傳失敗")
                    }

                    override fun onActionStart() {
                        getFastProgressDialog("正在上傳頭像……")
                    }

                    override fun onActionEnd(builder: MultipartBody.Builder) {

                        builder.addFormDataPart("uid", UserInfo.getInstance().user_id)
                        mPresent.uploadFile(0x1, RequestParamsHelper.LOGIN_MODEL, RequestParamsHelper.ACT_UPDATE_FACE, builder.build().parts())
                    }
                }).upload(arrayListOf(ImageBean(null, uri.path)))
            }
        }
    }

}