package com.app.phew.ui.friendRequest

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.listners.OnNotificationsListener
import com.app.phew.models.BaseResponse
import com.app.phew.models.friendRquestes.FriendRequestesResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_friend_request.*
import kotlinx.android.synthetic.main.no_data_layout.view.*

class FriendRequestActivity: ParentActivity() ,FriendRequestContract.View, OnNotificationsListener {
    override val layoutResource: Int
        get() = R.layout.activity_friend_request
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
            appCompatActivity.startActivity(Intent(appCompatActivity, FriendRequestActivity::class.java))
        }
    }



    private lateinit var mPresenter:FriendRequestPresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = FriendRequestPresenter(this)
        mPresenter.getRequests(mSharedPrefManager.authToken!!,mSharedPrefManager.userData.id!!,"friends")
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            mPresenter.getRequests(mSharedPrefManager.authToken!!,mSharedPrefManager.userData.id!!,"friends")
        }
        setToolbarTitle(getString(R.string.friend_requests))
    }

    override fun showFieldError(field: String) {}

    override fun onAcceptSuccess(response: BaseResponse) {
            swipeRefresh.isRefreshing = false
            SBManager.displayMessage(mContext, response.message ?: "")
        mPresenter.getRequests(mSharedPrefManager.authToken!!,mSharedPrefManager.userData.id!!,"friends")
    }

    override fun onResponseSuccess(data: FriendRequestesResponse) {
            swipeRefresh.isRefreshing = false
            if (data.data.isNullOrEmpty()) {
                noData.visibility = View.VISIBLE
                rvRequests.visibility = View.GONE
                noData.tvNoData.text = getString(R.string.no_friend_requests)
            } else {
                noData.visibility = View.GONE
                rvRequests.visibility = View.VISIBLE
                rvRequests.setItemViewCacheSize(data.data.size)
                rvRequests.adapter = FriendRequestAdapter(mContext, data.data, R.layout.recycler_item_friend_requestes, this)

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

    override fun onAcceptClick(position: Int, userId: Int, notificationId: String) {
        mPresenter.acceptFriendRequest(mSharedPrefManager.authToken!!,userId)
    }

    override fun onRejectClick(position: Int, userId: Int, notificationId: String) {
        mPresenter.rejectFriendRequest(mSharedPrefManager.authToken!!,userId)
    }
}