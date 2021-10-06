package com.app.phew.ui.signing.forgetPassword.verificationCode

import android.annotation.SuppressLint
import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.ui.signing.SignIngActivity
import com.app.phew.ui.signing.forgetPassword.enterMobile.EnterMobileActivity
import com.app.phew.ui.signing.forgetPassword.resetPassword.ResetPasswordActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_verification_code.*
import java.util.*
import java.util.concurrent.TimeUnit


class VerificationCodeActivity : ParentActivity(),VerificationCodeContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_verification_code
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
        fun startActivity(appCompatActivity: AppCompatActivity, mobile: String, flag: String) {
            appCompatActivity.
            startActivity(Intent(appCompatActivity, VerificationCodeActivity::class.java).putExtra("mobile", mobile).putExtra("flag", flag))
        }
    }


    private var mobile=""
    private var flag=""
    private var code = ""
    lateinit var mPresenter: VerificationCodePresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.barColor)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        mobile = intent?.extras?.getString("mobile")?:""
        flag = intent?.extras?.getString("flag")?:""
        mPresenter=VerificationCodePresenter(this)
        digits()
        setTimer()
        btn_send_code_again.setOnClickListener {
            mPresenter.resendCode(mobile)
            setTimer()
            btn_send_code_again.visibility = View.INVISIBLE
        }
        btnConfirmCode.setOnClickListener {
            code = edFirstDigit.text.toString()+edSecondDigit.text.toString()+ edThirdDigit.text.toString()+edFourthDigit.text.toString()
            mPresenter.verifyAccount(
                    mobile,code,flag
            )
        }
        tvSignIn.setOnClickListener {
            SignIngActivity.startActivity(this)
            finishAffinity()
        }
        btn_change_number.setOnClickListener {
            EnterMobileActivity.startActivity(this)
            finishAffinity()
        }
    }

    private fun setTimer(){
        val timer = object: CountDownTimer(65000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val text = String.format(Locale.ENGLISH,"%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)
                tv_send_code_timer.text = text

            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                tv_send_code_timer.text = "00:00"
                btn_send_code_again.visibility = View.VISIBLE
            }
        }
        timer.start()
    }

    private fun digits() {
        edSecondDigit.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_DEL) {
                if (edSecondDigit.text.toString().isEmpty()) {
                    edFirstDigit.requestFocus()
                }
            }
            false
        }

        edThirdDigit.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_DEL) {
                if (edThirdDigit.text.toString().isEmpty()) {
                    edSecondDigit.requestFocus()
                }
            }
            false
        }
        edFourthDigit.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_DEL) {
                if (edFourthDigit.text.toString().isEmpty()) {
                    edThirdDigit.requestFocus()
                }
            }
            false
        }
        edFirstDigit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (edFirstDigit.text!!.isNotEmpty()) edSecondDigit.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        edSecondDigit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (edSecondDigit.text!!.isNotEmpty()) edThirdDigit.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        edThirdDigit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (edThirdDigit.text!!.isNotEmpty()) edFourthDigit.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        edFourthDigit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // if (edFourthDigit.text!!.isNotEmpty())
            }

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
    }

    override fun showFieldError(field: String) {
        when (field){
            "code" -> {
                SBManager.displayError(this, getString(R.string.please_enter_valid_code))
            }
        }
    }

    override fun onResponseSuccess(data: BaseResponse) {
        CommonUtil.makeToast(this, data.message ?: "")
        if (flag=="forget"){
            ResetPasswordActivity.startActivity(this,mobile,code)
            finish()
        }else{
         SignIngActivity.startActivity(this)
         finish()
        }
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