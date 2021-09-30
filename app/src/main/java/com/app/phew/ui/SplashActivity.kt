package com.app.phew.ui

import android.content.Intent
import android.os.CountDownTimer
import android.view.WindowManager
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.ui.main.MainActivity
import com.app.phew.ui.signing.SignIngActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.LocaleHelper
import com.google.android.gms.common.GoogleApiAvailability
import net.alexandroid.gps.GpsStatusDetector

class SplashActivity : ParentActivity(), GpsStatusDetector.GpsStatusDetectorCallBack {
    override val layoutResource: Int
        get() = R.layout.activity_splash
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
            val mIntent = Intent(appCompatActivity, SplashActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            appCompatActivity.startActivity(mIntent)
        }
    }

    lateinit var fade: Animation
    var enabled = false
    lateinit var mGpsStatusDetector: GpsStatusDetector

    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        LocaleHelper.setLocale(mContext, "ar")
        mLanguagePrefManager.appLanguage = "ar"
        if (mSharedPrefManager.firstTime) {
            LocaleHelper.setLocale(mContext, "ar")
            mSharedPrefManager.firstTime = false
        } else LocaleHelper.setLocale(mContext,"ar")
          CommonUtil.getDeviceToken(mContext, mSharedPrefManager)
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) ==
            com.google.android.gms.common.ConnectionResult.SUCCESS
        ) mSharedPrefManager.mobileType = "android"
        else mSharedPrefManager.mobileType = "huawie"
        setSplash()
    }

    private fun setSplash() {
        val timer = object : CountDownTimer(1000, 2000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if (mSharedPrefManager.loginStatus || mSharedPrefManager.guest)
                    MainActivity.startActivity(mContext as AppCompatActivity)
                else SignIngActivity.startActivity(mContext as AppCompatActivity)
                finishAffinity()
            }
        }
        timer.start()
    }

    override fun onGpsAlertCanceledByUser() {
        mGpsStatusDetector.checkGpsStatus()
    }

    override fun onGpsSettingStatus(enabled: Boolean) {
        this.enabled = enabled
        if (enabled) setSplash()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode)
    }
}