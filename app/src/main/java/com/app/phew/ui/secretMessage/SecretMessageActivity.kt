package com.app.phew.ui.secretMessage

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
import kotlinx.android.synthetic.main.activity_send_secret_message.*

class SecretMessageActivity: ParentActivity() ,SecretMessageContract.View{
    override val layoutResource: Int
        get() = R.layout.activity_send_secret_message
    override val isEnableToolbar: Boolean
        get() = true
    override val isFullScreen: Boolean
        get() = false
    override val isEnableBack: Boolean
        get() = true
    override val isRecycle: Boolean
        get() = false

    override fun hideInputType() = false

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity,userId:Int) {
            appCompatActivity.
            startActivity(Intent(appCompatActivity, SecretMessageActivity::class.java).putExtra("userId",userId))
        }
    }



    private var userId=0
    lateinit var mPresenter: SecretMessagePresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        userId = intent?.extras?.getInt("userId")?:0
        mPresenter = SecretMessagePresenter(this)
        tvSend.setOnClickListener {
            if (etMessage.text.toString().isEmpty()){
                CommonUtil.makeToast(this,R.string.Write_your_message)
            }else{
                mPresenter.sendSecretMessage(
                    mSharedPrefManager.authToken!!,userId,etMessage.text.toString()
                )
            }
        }

    }

    override fun onResponseSuccess(data: BaseResponse) {
        CommonUtil.makeToast(this,data.message?:"")
        finish()
    }

    override fun onResponseError(errorBody: String) {
        SBManager.displayError(
            mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message!!,
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