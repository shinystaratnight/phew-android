package com.app.phew.ui.signing.login

import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.phew.R
import com.app.phew.base.BaseFragment
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.ui.main.MainActivity
import com.app.phew.ui.signing.SignIngActivity
import com.app.phew.ui.signing.forgetPassword.enterMobile.EnterMobileActivity
import com.app.phew.ui.signing.forgetPassword.verificationCode.VerificationCodeActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(), LoginContract.View {
    override val layoutResource: Int
        get() = R.layout.fragment_login
    override val isRecycle: Boolean
        get() = true

    lateinit var mPresenter: LoginPresenter

    override fun initializeComponents(view: View) {
        mPresenter = LoginPresenter(this)

        btn_login.setOnClickListener {
            mPresenter.login(et_email.text.toString(), et_password.text.toString(), mSharedPrefManager.mobileType, mSharedPrefManager.deviceToken)
        }
        btn_forget_pass.setOnClickListener {
            EnterMobileActivity.startActivity(mContext as AppCompatActivity)
        }

        btn_google.setOnClickListener { (requireActivity() as SignIngActivity).goLogin() }
    }

    override fun showFieldError(field: String) {
        when (field) {
            "identifier" -> et_email.error = getString(R.string.please_enter_valid_email_mobile)
            "password" -> et_password.error = getString(R.string.please_enter_valid_password)
        }
    }

    override fun onSocialLogin(data: LoginResponse) {
        SBManager.displayMessage(mContext, data.message.toString())
        if (data.data != null) {
            mSharedPrefManager.userData = data.data
            if (data.data.token != null) mSharedPrefManager.authToken =
                    "${data.data.token.token_type.toString()} ${data.data.token.access_token.toString()}"
            mSharedPrefManager.loginStatus = true
            mSharedPrefManager.guest = false
            MainActivity.startActivity(mContext as AppCompatActivity)
        }
    }

    override fun onResponseSuccess(data: LoginResponse) {
        SBManager.displayMessage(mContext, data.message.toString())
        if (data.data != null) {
            mSharedPrefManager.userData = data.data
            if (data.data.token != null) mSharedPrefManager.authToken =
                    "${data.data.token.token_type.toString()} ${data.data.token.access_token.toString()}"
            mSharedPrefManager.loginStatus = true
            mSharedPrefManager.guest = false
            MainActivity.startActivity(mContext as AppCompatActivity)
        }
    }

    override fun onResponseError(errorBody: String) {
        if (Gson().fromJson(errorBody, BaseResponse::class.java).is_active != null) {
            if (!Gson().fromJson(errorBody, BaseResponse::class.java).is_active!!) {
                CommonUtil.makeToast(mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message.toString())
                VerificationCodeActivity.startActivity(mContext as AppCompatActivity, et_email.text.toString(), "active")
            } else {
                SBManager.displayError(
                        mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message.toString())
            }

        } else {
            SBManager.displayError(
                    mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message.toString())
        }
    }

    override fun onResponseFailure(t: Throwable) {
        CommonUtil.handleException(mContext, t)
    }

    override fun showProgress() {
        if (activity != null && isAdded) {
            rel.visibility = View.VISIBLE
            requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    override fun hideProgress() {
        if (activity != null && isAdded) {
            rel.visibility = View.GONE
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

}