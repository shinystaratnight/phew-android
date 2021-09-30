package com.app.phew.ui.followUs

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.followUs.FollowUsResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_follow_us.*

class FollowUsActivity : ParentActivity(), FollowUsContract.View {

    override val layoutResource: Int
        get() = R.layout.activity_follow_us
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
                Intent(appCompatActivity, FollowUsActivity::class.java)
            )
        }
    }

    private lateinit var mPresenter: FollowUsPresenter
    override fun initializeComponents() {
        mPresenter = FollowUsPresenter(this)
        mPresenter.followUs()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setToolbarTitle(getString(R.string.call_us))
        followUsSwipe.setColorSchemeColors(Color.parseColor("#E21E2C"))
        followUsSwipe.setOnRefreshListener {
            mPresenter.followUs()
        }

    }



    override fun onResponseSuccess(data: FollowUsResponse) {
        followUsSwipe.isRefreshing = false
        phone.text = data.data.mobile
        whatsApp.text = data.data.whatsapp_phone
        phone.setOnClickListener {
            call(data.data.mobile)
        }
        mail.text = data.data.email
        mail.setOnClickListener {
            openEmail(data.data.email)
        }
        whatsApp.setOnClickListener {
            openWhatsAppConversationUsingUri(data.data.whatsapp_phone)
        }
        imvFacebook.setOnClickListener {
            openSocial(data.data.facebook_url,"face")
        }
        imvTwitter.setOnClickListener {
            openSocial(data.data.twitter_url,"twitter")
        }
        imvInsta.setOnClickListener {
            openSocial(data.data.instagram_url,"insta")
        }

    }

   private fun openEmail (email:String){
       val emailIntent = Intent(Intent.ACTION_SEND)
       emailIntent.type = "plain/text"
       emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(email))
       emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
       emailIntent.putExtra(Intent.EXTRA_TEXT, "")
       this.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
   }

    private fun call(phone: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phone")
        startActivity(callIntent)
    }

    private fun openSocial(url:String?, type:String) {
        var uri = Uri.parse(url)
        if (type == "face"){
            uri = Uri.parse("fb://facewebmodal/f?href=$url")
        }

        val likeIng = Intent(Intent.ACTION_VIEW, uri)
        Intent.createChooser(likeIng,"")
        when(type){
            "insta" ->{
                likeIng.setPackage("com.instagram.android")
            }
            "linked"->{
                likeIng.setPackage("com.linkedin.android")
            }
            "twitter"->{
                likeIng.setPackage("com.twitter.android")
            }
            "snap"->{
                likeIng.setPackage("com.snapchat.android")
            }
            "face"->{
                if ( try {
                        this.packageManager.getApplicationInfo("com.facebook.katana", 0)
                        true
                    } catch (e: PackageManager.NameNotFoundException) {
                        false
                    }){
                    likeIng.setPackage("com.facebook.katana")
                }
                /* */
            }
        }

        var valid = false
        if (!url.isNullOrEmpty()){
            if (likeIng.resolveActivity(mContext.packageManager) != null) {
                valid = when {
                    type == "face" && !url.contains("facebook") -> false
                    type == "insta" && !url.contains("instagram") -> false
                    type == "linked" && !url.contains("linkedin") -> false
                    type == "twitter" && !url.contains("twitter") -> false
                    type == "snap" && !url.contains("snapchat") -> false
                    else -> true
                }
            }
            if (valid){
                try {

                    mContext.startActivity(likeIng)
                } catch (e: ActivityNotFoundException) {
                    mContext.startActivity(
                        likeIng
                    )
                }
            }
        }else {
            CommonUtil.makeToast(this,getString(R.string.no_suitable_app))
        }

    }


    fun openWhatsAppConversationUsingUri( numberWithCountryCode:String) {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=" + numberWithCountryCode )
        val sendIntent = Intent(Intent.ACTION_VIEW, uri)
        this.startActivity(sendIntent)
    }
    override fun onResponseError(errorBody: String) {
        followUsSwipe.isRefreshing = false
        SBManager.displayError(this, Gson().fromJson(errorBody, BaseResponse::class.java).message?:"")
    }

    override fun onResponseFailure(t: Throwable) {
        followUsSwipe.isRefreshing = false
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