package com.app.phew.ui.showProfile

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.ui.chatDetails.ChatDetailsActivity
import com.app.phew.ui.home.HomeFragment
import com.app.phew.ui.myFriendes.MyFriendsActivity
import com.app.phew.ui.phewPremiumMemberShip.PhewPremiumMemberShipActivity
import com.app.phew.ui.secretMessage.SecretMessageActivity
import com.app.phew.ui.secretMessages.SecretMessagesActivity
import com.app.phew.ui.settings.SettingsActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_show_profile.*
import kotlinx.android.synthetic.main.app_bar_profile.*

class ShowProfileActivity  : ParentActivity() , ShowProfileContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_show_profile
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
        fun startActivity(appCompatActivity: AppCompatActivity,id:Int) {
            appCompatActivity.
            startActivity(Intent(appCompatActivity, ShowProfileActivity::class.java).putExtra("id",id))
        }
    }

    lateinit var mPresenter: ShowProfilePresenter
    var id  = 1
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = ShowProfilePresenter(this)
        id = intent?.extras?.getInt("id")?:1
        mPresenter.getProfile(
            mSharedPrefManager.authToken!!, id
        )
        if (mSharedPrefManager.userData.id==id){
            btnAddFriend.visibility = View.INVISIBLE
            btnFollow.visibility = View.INVISIBLE
            imvMessages.visibility = View.INVISIBLE
            imvQs.visibility = View.INVISIBLE
        }

        imvQs.setOnClickListener { SecretMessageActivity.startActivity(this,id) }
        imvMessages.setOnClickListener {
            ChatDetailsActivity.startActivity(this,id)
        }
        imvAll.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_all_d_1))
        imvLocation.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_location_profile))
        imvWatching.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_television_profile_d))
        imvStar.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_profile_star))
        replace("users/$id/posts?type=normal")
        imvAll.setOnClickListener {
            imvAll.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_all_d_1))
            imvLocation.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_location_profile))
            imvWatching.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_television_profile_d))
            imvStar.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_profile_star))
            replace("users/$id/posts?type=normal")
        }

        imvLocation.setOnClickListener {
            imvAll.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_all_d))
            imvLocation.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_location_profile_a))
            imvWatching.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_television_profile_d))
            imvStar.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_profile_star))
            replace("users/$id/posts?type=location")
        }

        imvWatching.setOnClickListener {
            imvAll.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_all_d))
            imvLocation.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_location_profile))
            imvWatching.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_create_watching))
            imvStar.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_profile_star))
            replace("users/$id/posts?type=watching")
        }

        imvStar.setOnClickListener {
            imvAll.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_all_d))
            imvLocation.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_location_profile))
            imvWatching.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_television_profile_d))
            imvStar.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_star_profile_a))
            replace("users/$id/posts?type=fav")
        }

        imvPhewPremium.setOnClickListener {
            PhewPremiumMemberShipActivity.startActivity(this)
        }
        imvPhewSettings.setOnClickListener {
            SettingsActivity.startActivity(this)
        }
        if (mSharedPrefManager.userData.id==id){
            tvFriendsNum.setOnClickListener { MyFriendsActivity.startActivity(this)  }
            tvFriendsNumValue.setOnClickListener { MyFriendsActivity.startActivity(this)  }
        }


    }

    fun replace(url:String){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMainClient, HomeFragment("most",url))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun onFollowSuccess(response: BaseResponse) {
       CommonUtil.makeToast(this,response.message?:"")
        mPresenter.getProfile(
            mSharedPrefManager.authToken!!, id
        )
    }

    override fun onAcceptSuccess(response: BaseResponse) {
        CommonUtil.makeToast(this,response.message?:"")
        mPresenter.getProfile(
            mSharedPrefManager.authToken!!, id
        )
    }

    override fun onResponseSuccess(data: LoginResponse) {

        if (data.data!!.is_friend!!){
            Log.e("FFF","isFriend")
            btnAddFriend.text = getString(R.string.remove_friends)
            btnAddFriend.setOnClickListener {
                mPresenter.cancelRequest(mSharedPrefManager.authToken!!,id)
            }
            btnAccept.visibility = View.GONE
            btnRefuse.visibility = View.GONE
        }else{
            if (data.data.is_friend_request!!){
                if (data.data.sender_friend_request=="me"){
                    Log.e("FFF","isMe")
                    btnAddFriend.text = getString(R.string.cancel)
                    btnAddFriend.setOnClickListener {
                        mPresenter.cancelRequest(mSharedPrefManager.authToken!!,id)
                    }
                    btnAccept.visibility = View.GONE
                    btnRefuse.visibility = View.GONE
                }else{
                    Log.e("FFF","isYou")
                    btnAccept.visibility = View.VISIBLE
                    btnRefuse.visibility = View.VISIBLE
                    btnAddFriend.visibility = View.GONE
                    btnAccept.setOnClickListener {
                        mPresenter.acceptFriendRequest(mSharedPrefManager.authToken!!,id)
                    }
                    btnRefuse.setOnClickListener {
                        mPresenter.rejectFriendRequest(mSharedPrefManager.authToken!!,id)
                    }
                }
            }else{
                Log.e("FFF","notFriend")
                btnAddFriend.text = getString(R.string.add_friends)
                btnAddFriend.setOnClickListener {
                    mPresenter.sendRequest(mSharedPrefManager.authToken!!,id)
                }
                btnAccept.visibility = View.GONE
                btnRefuse.visibility = View.GONE
            }
        }



        if (data.data!!.is_follow!!){
            btnFollow.text = getString(R.string.un_follow)
        }else{
            btnFollow.text = getString(R.string.follow)
        }
        btnFollow.setOnClickListener {
            mPresenter.follow(mSharedPrefManager.authToken!!,id)
        }
        if (data.data.is_verified!!) appCompatImageView4.visibility = View.INVISIBLE
        if (data.data.is_subscribed!!) appCompatImageView5.visibility = View.INVISIBLE
        tvFriendsNumValue.text = data.data.friends_count.toString()
        tvPostsNumValue.text = data.data.posts_count.toString()
        tvFollowersNumValue.text = data.data.follower_count.toString()
        tvFollowingNumValue.text = data.data.following_count.toString()
        val sliderImages = ArrayList<SlideModel>()
        if (data.data.profile_images!!.isEmpty()){
            sliderImages.add(SlideModel(data.data.profile_image))
        }else{
            for (slide in data.data.profile_images) sliderImages.add(SlideModel(slide.image))
        }

       isHomeSlider.setImageList(sliderImages, ScaleTypes.CENTER_INSIDE)

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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun hideProgress() {
        rel.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}