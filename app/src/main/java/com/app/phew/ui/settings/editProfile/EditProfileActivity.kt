package com.app.phew.ui.settings.editProfile

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.images.ImageModel
import com.app.phew.ui.editPassword.EditPasswordActivity
import com.app.phew.ui.main.MainActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_profile.*


class EditProfileActivity : ParentActivity(), EditProfileContract.View, EditProfileImageAdapter.OnDeleteClick {
    override val layoutResource: Int
        get() = R.layout.activity_edit_profile
    override val isEnableToolbar: Boolean
        get() = true
    override val isFullScreen: Boolean
        get() = false
    override val isEnableBack: Boolean
        get() = true
    override val isRecycle: Boolean
        get() = false

    override fun hideInputType() = true

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity) {
            appCompatActivity.startActivity(
                Intent(appCompatActivity, EditProfileActivity::class.java)
            )
        }
    }

    lateinit var mPresenter: EditProfilePresenter
    var imagesList: ArrayList<ImageModel> = ArrayList()
    lateinit var imagesAdapter: EditProfileImageAdapter
    override fun initializeComponents() {
        setToolbarTitle(getString(R.string.edit_profile))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = EditProfilePresenter(this)
        imagesList.addAll(mSharedPrefManager.userData.profile_images!!)
        imagesAdapter = EditProfileImageAdapter(mContext, imagesList, R.layout.item_image, this)
        rvImages.adapter = imagesAdapter
        Glide.with(this).load(mSharedPrefManager.userData.profile_image).into(circleImageView)
        tvFullName.text = mSharedPrefManager.userData.fullname
        et_name.setText(mSharedPrefManager.userData.fullname)
        et_email.setText(mSharedPrefManager.userData.email)
        et_phone.setText(mSharedPrefManager.userData.mobile)
        imvEditPicture.setOnClickListener {

            if (mSharedPrefManager.userData.is_subscribed!!){
                if (imagesList.size==5)
                    SBManager.displayError(
                        mContext,
                        getString(R.string.you_are_not_allowed_to_add_more_than_picture_5)
                    )
                else pickImages()
            }else{
                if (imagesList.size==3)
                    SBManager.displayError(
                        mContext,
                        getString(R.string.you_are_not_allowed_to_add_more_than_picture)
                    )
                else pickImages()
            }

        }
        btnSaveChanges.setOnClickListener {
            var newImages :ArrayList<ImageModel> = ArrayList()
            newImages.clear()
            for (i in imagesList){
                if (i.id!!<0){
                    newImages.add(i)
                }
            }
            mPresenter.updateProfile(
                mSharedPrefManager.authToken!!,
                et_name.text.toString(),
                et_phone.text.toString(),
                et_email.text.toString(),
                if (newImages.isNotEmpty()) {
                    newImages
                } else null
            )
        }
        imvEditPassword.setOnClickListener {
            EditPasswordActivity.startActivity(this)
        }
    }


    override fun showFieldError(field: String) {
        when (field) {
            "fullname" -> {
                SBManager.displayError(mContext, getString(R.string.please_enter_name))
            }
            "email" -> {
                SBManager.displayError(mContext, getString(R.string.please_enter_valid_email))
            }
            "notEmail" -> {
                SBManager.displayError(mContext, getString(R.string.please_enter_valid_email))
            }
            "mobile" -> {
                SBManager.displayError(
                    mContext,
                    getString(R.string.please_enter_valid_mobile_numbe)
                )
            }
        }
    }


    private fun pickImages() {
        ImagePicker.create(this)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .includeVideo(false) // Show video on image picker
                .single()// multi mode (default mode)
                .limit(1) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(true) // disabling log
                .start(); // start image picker activity with request code
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data).path
            imagesList.add(ImageModel(-1, image))
            imagesAdapter.notifyDataSetChanged()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun deletePictureSuccess(response: BaseResponse) {
        SBManager.displayMessage(mContext, response.message ?: "")
        imagesAdapter.notifyDataSetChanged()
    }

    override fun onResponseSuccess(data: LoginResponse) {
       MainActivity.startActivity(this)
       finishAffinity()
    }

    override fun onResponseError(errorBody: String) {
        SBManager.displayError(
            mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message ?: ""
        )
    }

    override fun onResponseFailure(t: Throwable) {
        CommonUtil.handleException(mContext, t)
    }

    override fun showProgress() {
        rel.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    override fun hideProgress() {
        rel.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onDeleteClick(position: Int, model: ImageModel) {
        if (model.id!!<0){
            imagesList.removeAt(position)
            imagesAdapter.notifyItemRemoved(position)
            imagesAdapter.notifyDataSetChanged()
        }else{
            imagesList.removeAt(position)
            mPresenter.deletePicture(mSharedPrefManager.authToken!!, model.id)

        }
    }

}