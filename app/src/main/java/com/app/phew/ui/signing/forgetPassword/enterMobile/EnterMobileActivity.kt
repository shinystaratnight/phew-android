package com.app.phew.ui.signing.forgetPassword.enterMobile

import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.ui.signing.SignIngActivity
import com.app.phew.ui.signing.forgetPassword.verificationCode.VerificationCodeActivity
import com.app.phew.ui.signing.register.RegisterPresenter
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_enter_mobile.*

class EnterMobileActivity : ParentActivity(),EnterMobileContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_enter_mobile
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
        fun startActivity(appCompatActivity: AppCompatActivity) {
            appCompatActivity.startActivity(Intent(appCompatActivity, EnterMobileActivity::class.java))
        }
    }



    lateinit var mPresenter: EnterMobilePresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.barColor)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        mPresenter = EnterMobilePresenter(this)
        btnSendCode.setOnClickListener {
            mPresenter.forgetPassword(
                    et_email.text.toString()
            )
        }
        tvSignIn.setOnClickListener {
            SignIngActivity.startActivity(this)
            finishAffinity()
        }

    }

    override fun showFieldError(field: String) {
        when (field){
            "shortMobile" -> {
                SBManager.displayError(this, getString(R.string.please_enter_valid_mobile_numbe))
            }
            "mobile" -> {
              SBManager.displayError(this, getString(R.string.please_enter_valid_mobile_numbe))
            }
        }
    }

    override fun onResponseSuccess(data: BaseResponse) {
        CommonUtil.makeToast(this,data.message?:"")
        VerificationCodeActivity.startActivity(this,et_email.text.toString(),"forget")
        finish()
    }

    override fun onResponseError(errorBody: String) {
        SBManager.displayError(this, Gson().fromJson(errorBody, BaseResponse::class.java).message?:"")
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