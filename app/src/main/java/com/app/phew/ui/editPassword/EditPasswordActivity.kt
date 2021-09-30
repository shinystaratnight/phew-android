package com.app.phew.ui.editPassword

import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_password.*

class EditPasswordActivity : ParentActivity(), EditPasswordContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_edit_password
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
                    Intent(appCompatActivity, EditPasswordActivity::class.java)
            )
        }
    }

    lateinit var mPresenter: EditPasswordPresenter
    override fun initializeComponents() {
        setToolbarTitle(getString(R.string.edit_password))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = EditPasswordPresenter(this)
        btnSaveNewPassword.setOnClickListener {
            if (etNewPassword.text.toString() != etConfirmPassword.text.toString())
                SBManager.displayError(mContext, getString(R.string.passwords_not_match))
            else mPresenter.editPassword(
                    mSharedPrefManager.authToken!!,
                    et_current_password.text.toString(), etConfirmPassword.text.toString()
            )
        }
    }


    override fun showFieldError(field: String) {
        when (field) {
            "oldPassword" ->
                SBManager.displayError(mContext, getString(R.string.please_enter_current_password))
            "shortPassword" ->
                SBManager.displayError(mContext, getString(R.string.password_is_too_short))
        }
    }

    override fun onResponseSuccess(data: BaseResponse) {
        SBManager.displayMessage(mContext, data.message ?: "")
        finish()
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
}