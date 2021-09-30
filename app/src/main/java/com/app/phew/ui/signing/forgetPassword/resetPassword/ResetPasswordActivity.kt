package com.app.phew.ui.signing.forgetPassword.resetPassword

import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.ui.signing.SignIngActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : ParentActivity(),ResetPasswordContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_reset_password
    override val isEnableToolbar: Boolean
        get() = false
    override val isFullScreen: Boolean
        get() = false
    override val isEnableBack: Boolean
        get() = false
    override val isRecycle: Boolean
        get() = false

    override fun hideInputType() = false

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity,mobile:String,code:String) {
            appCompatActivity.
            startActivity(Intent(appCompatActivity, ResetPasswordActivity::class.java)
                    .putExtra("mobile",mobile).putExtra("code",code))
        }
    }


    lateinit var mPresenter: ResetPasswordPresenter
    private var mobile = ""
    private var code = ""
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.barColor)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        mobile = intent?.extras?.getString("mobile")?:""
        code = intent?.extras?.getString("code")?:""
        mPresenter = ResetPasswordPresenter(this)
        btnSaveNewPassword.setOnClickListener {
            mPresenter.resetPassword(
               mobile,code,et_password.text.toString(),et_confirm_password.text.toString()
            )
        }
    }

    override fun showFieldError(field: String) {
        when (field){
            "password" -> {

                SBManager.displayError(mContext, getString(R.string.please_enter_valid_password))
            }
            "notMatch" -> {
                SBManager.displayError(mContext, getString(R.string.passwords_not_match))
            }
        }
    }

    override fun onResponseSuccess(data: BaseResponse) {
        CommonUtil.makeToast(this, data.message ?: "")
        SignIngActivity.startActivity(this)
        finish()
    }

    override fun onResponseError(errorBody: String) {
        SBManager.displayError(this, Gson().fromJson(errorBody, BaseResponse::class.java).message
                ?: "")
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