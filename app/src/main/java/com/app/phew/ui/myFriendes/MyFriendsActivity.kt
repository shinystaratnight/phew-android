package com.app.phew.ui.myFriendes

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.friends.FriendModel
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_my_friends.*
import kotlinx.android.synthetic.main.no_data_layout.view.*

class MyFriendsActivity : ParentActivity(),MyFriendsContract.View, MyFriendsAdapter.OnDeleteClick {
    override val layoutResource: Int
        get() = R.layout.activity_my_friends
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
            appCompatActivity.startActivity(Intent(appCompatActivity, MyFriendsActivity::class.java))
        }
    }



    private lateinit var mPresenter: MyFriendsPresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = MyFriendsPresenter(this)
        mPresenter.getFriends(mSharedPrefManager.authToken!!,mSharedPrefManager.userData.id!!)
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            mPresenter.getFriends(mSharedPrefManager.authToken!!,mSharedPrefManager.userData.id!!)
        }

    }



    override fun onRemoveFriends(data: BaseResponse) {
        CommonUtil.makeToast(this,data.message?:"")
        mPresenter.getFriends(mSharedPrefManager.authToken!!,mSharedPrefManager.userData.id!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onResponseSuccess(data: FriendsResponse) {
        swipeRefresh.isRefreshing = false
        tvFriends.text = getString(R.string.you_have_now)+" "+data.data!!.size+" "+getString(R.string.friend)
        if (data.data.isNullOrEmpty()) {
            noData.visibility = View.VISIBLE
            rvFriends.visibility = View.GONE
            noData.tvNoData.text = getString(R.string.no_packages)
        } else {
            noData.visibility = View.GONE
            rvFriends.visibility = View.VISIBLE
            rvFriends.setItemViewCacheSize(data.data.size)
            rvFriends.adapter = MyFriendsAdapter(mContext, data.data, R.layout.recycler_item_my_friends, this)
        }
    }

    override fun onResponseError(errorBody: String) {
        swipeRefresh.isRefreshing = false
        SBManager.displayError(
                mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message!!,
        )
    }

    override fun onResponseFailure(t: Throwable) {
        swipeRefresh.isRefreshing = false
        CommonUtil.handleException(mContext, t)
    }

    override fun showProgress() {
        swipeRefresh.isRefreshing = false
        rel.visibility = View.VISIBLE
        window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )

    }

    override fun hideProgress() {
        swipeRefresh.isRefreshing = false
        rel.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onDeleteClick(position: Int, model: FriendModel) {
        mPresenter.removeFriend(mSharedPrefManager.authToken!!,model.user!!.id!!)
    }


}