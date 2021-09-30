package com.app.phew.ui.notifications

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.phew.R
import com.app.phew.base.BaseFragment
import com.app.phew.listners.OnNotificationsListener
import com.app.phew.models.BaseResponse
import com.app.phew.models.notifications.NotificationsResponse
import com.app.phew.ui.friendRequest.FriendRequestActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.no_data_layout.view.*

class NotificationsFragment : BaseFragment(),NotificationsContract.View, OnNotificationsListener {
    override val layoutResource: Int
        get() = R.layout.fragment_notifications
    override val isRecycle: Boolean
        get() = false

     lateinit var mPresenter:NotificationsPresenter
     private var notificationId=""
     private var count=0
    override fun initializeComponents(view: View) {
        mPresenter=NotificationsPresenter(this)
        mPresenter.getNotifications(mSharedPrefManager.authToken!!)
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            count=0
            mPresenter.getNotifications(mSharedPrefManager.authToken!!)
        }
        friendRequest.setOnClickListener {
          FriendRequestActivity.startActivity(mContext as AppCompatActivity)
        }
        tvCount.setOnClickListener {
            FriendRequestActivity.startActivity(mContext as AppCompatActivity)
        }
    }

    override fun showFieldError(field: String) {}

    override fun onDeleteSuccess(response: BaseResponse) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            mPresenter.getNotifications(mSharedPrefManager.authToken!!)
        }
    }

    override fun onAcceptSuccess(response: BaseResponse) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            mPresenter.deleteNotifications(mSharedPrefManager.authToken!!, notificationId)
            SBManager.displayMessage(mContext, response.message ?: "")
        }
    }

    override fun onResponseSuccess(data: NotificationsResponse) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            if (data.data.isNullOrEmpty()) {
                noData.visibility = View.VISIBLE
                rvNotifications.visibility = View.GONE
                noData.tvNoData.text = getString(R.string.no_notifications)
            } else {
                for (i in data.data){
                    if (i.key=="new_friend_request") count++
                }
                tvCount.text = count.toString()
                noData.visibility = View.GONE
                rvNotifications.visibility = View.VISIBLE
                rvNotifications.setItemViewCacheSize(data.data.size)
                rvNotifications.adapter = NotificationsAdapter(mContext, data.data, R.layout.recycler_item_notification, this)

            }
        }
    }

    override fun onResponseError(errorBody: String) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            SBManager.displayError(
                    mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message!!,
            )
        }
    }

    override fun onResponseFailure(t: Throwable) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            CommonUtil.handleException(mContext, t)
        }
    }

    override fun showProgress() {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            rel.visibility = View.VISIBLE
            requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    override fun hideProgress() {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            rel.visibility = View.GONE
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onAcceptClick(position: Int, userId: Int, notificationId: String) {
        mPresenter.acceptFriendRequest(mSharedPrefManager.authToken!!,userId)
        this.notificationId = notificationId
    }

    override fun onRejectClick(position: Int, userId: Int, notificationId: String) {
        mPresenter.rejectFriendRequest(mSharedPrefManager.authToken!!,userId)
        this.notificationId = notificationId
    }
}