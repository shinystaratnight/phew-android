package com.app.phew.ui.settings.premium

import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.ui.settings.notificationSettings.NotificationsSettingsContract
import com.app.phew.ui.settings.notificationSettings.NotificationsSettingsPresenter
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_premium_member_ship.*

class PremiumMemberShipActivity : ParentActivity(), NotificationsSettingsContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_premium_member_ship
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
                    PremiumMemberShipActivity::class.java
                )
            )
        }
    }

    lateinit var mPresenter: NotificationsSettingsPresenter
    override fun initializeComponents() {
        setToolbarTitle(getString(R.string.premium_membership_settings))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = NotificationsSettingsPresenter(this)
        mPresenter.getProfile(
            mSharedPrefManager.authToken!!, mSharedPrefManager.userData.id!!
        )
        swDeleteFriends.setOnClickListener {
            mPresenter.updatePackageSettings(
                mSharedPrefManager.authToken!!,"PUT",if (swDeleteFriends.isChecked)1 else 0
            )
        }
    }

    override fun onResponseSuccess(data: LoginResponse) {
        swDeleteFriends.isChecked = data.data?.user_settings?.delete_inactive_followers_and_friends == 1
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