package com.app.phew.ui.aboutApp

import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.aboutApp.AboutAppResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_about_app.*

class AboutAppActivity : ParentActivity() , AboutAppContract.View{
    override val layoutResource: Int
        get() = R.layout.activity_about_app
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
        fun startActivity(appCompatActivity: AppCompatActivity,flag:String) {
            appCompatActivity.
            startActivity(Intent(appCompatActivity, AboutAppActivity::class.java).putExtra("flag",flag))
        }
    }



    private lateinit var mPresenter: AboutAppPresenter
    private var flag = ""
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = AboutAppPresenter(this)
        flag = intent?.extras?.getString("flag")?:""
        if (flag=="terms"){
            mPresenter.getTerms()
            setToolbarTitle(getString(R.string.usage_policy))
        }else{
            mPresenter.getAbout()
            setToolbarTitle(getString(R.string.about_app))
        }
    }

    override fun onResponseSuccess(data: AboutAppResponse) {
        if (flag=="terms"){
            tvBody.text = data.data.conditions_terms
        }else{
            tvBody.text = data.data.about
        }
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