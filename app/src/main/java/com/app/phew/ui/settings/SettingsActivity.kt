package com.app.phew.ui.settings

import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.ui.SplashActivity
import com.app.phew.ui.aboutApp.AboutAppActivity
import com.app.phew.ui.followUs.FollowUsActivity
import com.app.phew.ui.settings.editProfile.EditProfileActivity
import com.app.phew.ui.settings.notificationSettings.NotificationsSettingsActivity
import com.app.phew.ui.settings.premium.PremiumMemberShipActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : ParentActivity() ,SettingsContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_settings
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
            appCompatActivity.startActivity(Intent(appCompatActivity, SettingsActivity::class.java))
        }
    }

    lateinit var mPresenter: SettingsPresenter
    override fun initializeComponents() {
        setToolbarTitle(getString(R.string.settings))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        tvNotifications.setOnClickListener {
            NotificationsSettingsActivity.startActivity(this)
        }
        tvPremium.setOnClickListener {
            PremiumMemberShipActivity.startActivity(this)
        }
        tvAboutApp.setOnClickListener {
            AboutAppActivity.startActivity(this,"about")
        }
        tvUsagePolicy.setOnClickListener {
            AboutAppActivity.startActivity(this,"terms")
        }
        tvCallUs.setOnClickListener {
            FollowUsActivity.startActivity(this)
        }
        tvEditProfile.setOnClickListener {
            EditProfileActivity.startActivity(this)
        }
        mPresenter =SettingsPresenter(this)
        mPresenter.getProfile(
                mSharedPrefManager.authToken!!,mSharedPrefManager.userData.id!!
        )
        tvLogout.setOnClickListener {
            mPresenter.logout(mSharedPrefManager.authToken!!,mSharedPrefManager.mobileType,mSharedPrefManager.deviceToken)
        }
    }

    override fun onLogoutSuccess(response: BaseResponse) {
        mSharedPrefManager.logout()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id)).requestEmail().build())
        mGoogleSignInClient.signOut()
        mGoogleSignInClient.revokeAccess()
        SplashActivity.startActivity(this)
        finishAffinity()
    }

    override fun onResponseSuccess(data: LoginResponse) {
        mSharedPrefManager.userData = data.data!!
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