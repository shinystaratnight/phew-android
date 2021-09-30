package com.app.phew.ui.settings.notificationSettings

import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_notifcations_settings.*

class NotificationsSettingsActivity : ParentActivity(), NotificationsSettingsContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_notifcations_settings
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
        fun startActivity(appCompatActivity: AppCompatActivity) {
            appCompatActivity.startActivity(
                Intent(
                    appCompatActivity,
                    NotificationsSettingsActivity::class.java
                )
            )
        }
    }

    lateinit var mPresenter: NotificationsSettingsPresenter
    override fun initializeComponents() {
        setToolbarTitle(getString(R.string.notifications))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = NotificationsSettingsPresenter(this)
        mPresenter.getProfile(
            mSharedPrefManager.authToken!!, mSharedPrefManager.userData.id!!
        )
        swAllNotifications.setOnClickListener {
            mPresenter.updateSettings(
                mSharedPrefManager.authToken!!,
                "PUT",
                if (swAllNotifications.isChecked) 1 else 0,
                null,
                null
            )
        }

        swNewFollowers.setOnClickListener {
            mPresenter.updateSettings(
                mSharedPrefManager.authToken!!,
                "PUT",
               null,
                if (swNewFollowers.isChecked) 1 else 0,
                null
            )
        }
        swMentions.setOnClickListener {
            mPresenter.updateSettings(
                mSharedPrefManager.authToken!!,
                "PUT",
                null,
                null,
                if (swMentions.isChecked) 1 else 0
            )
        }
    }

    override fun onResponseSuccess(data: LoginResponse) {
        swAllNotifications.isChecked = data.data?.user_settings?.all_notices == 1
        swNewFollowers.isChecked = data.data?.user_settings?.notification_to_new_followers == 1
        swMentions.isChecked = data.data?.user_settings?.notification_to_mention == 1
        mSharedPrefManager.userData = data.data!!
        if (data.data.token != null) mSharedPrefManager.authToken =
            "${data.data.token.token_type.toString()} ${data.data.token.access_token.toString()}"
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