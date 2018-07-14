package com.android.ql.restaurant.ui.fragment.mine

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.android.ql.restaurant.R
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment
import com.android.ql.restaurant.utils.*
import com.soundcloud.android.crop.Crop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_personal_info_layout.*
import org.jetbrains.anko.support.v4.toast
import java.io.File

class MinePersonalInfoFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_personal_info_layout

    override fun initView(view: View?) {
        mEtPersonalInfoNickName.hint = "Mary 吳"
        mEtPersonalInfoNickName.setSelection(mEtPersonalInfoNickName.getTextString().lastIndex+1)
        mRlPersonalInfoFace.setOnClickListener {
            openImageChoose(MimeType.ofImage(),1)
        }
        mBtPersonalInfoSave.setOnClickListener {
            if (mEtPersonalInfoNickName.isEmpty()){
                toast("請輸入昵稱")
                return@setOnClickListener
            }
            mContext.hiddenKeyBoard(mEtPersonalInfoNickName.windowToken)
            toast("保存成功")
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
                GlideManager.loadCircleImage(mContext,uri.path,mIvPersonalInfoFace)

//                ImageUploadHelper(object : ImageUploadHelper.OnImageUploadListener {
//                    override fun onActionFailed() {
//                        toast("头像上传失败，请稍后重试！")
//                    }
//
//                    override fun onActionStart() {
//                        getFastProgressDialog("正在上传头像……")
//                    }
//
//                    override fun onActionEnd(builder: MultipartBody.Builder) {
//                        builder.addFormDataPart("uid", UserInfo.getInstance().user_id)
//                        mPresent.uploadFile(0x1, RequestParamsHelper.USER_MODEL, RequestParamsHelper.ACT_UPDATE_FACE, builder.build().parts())
//                    }
//                }).upload(arrayListOf(ImageBean(null, uri.path)))
            }
        }
    }

}