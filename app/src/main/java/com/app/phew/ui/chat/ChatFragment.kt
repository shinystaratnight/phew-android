package com.app.phew.ui.chat

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.app.phew.R
import com.app.phew.base.BaseFragment
import com.app.phew.models.BaseResponse
import com.app.phew.models.chats.ChatsModel
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.ui.chatDetails.ChatDetailsActivity
import com.app.phew.ui.secretMessages.SecretMessagesActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.PermissionUtils
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import com.karan.churi.PermissionManager.PermissionManager
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_notifications.noData
import kotlinx.android.synthetic.main.fragment_notifications.rel
import kotlinx.android.synthetic.main.fragment_notifications.swipeRefresh
import kotlinx.android.synthetic.main.no_data_layout.view.*

class ChatFragment : BaseFragment(),ChatContract.View, ChatsAdapter.OnChatClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_chat
    override val isRecycle: Boolean
        get() = false

    lateinit var mPresenter: ChatPresenter
    override fun initializeComponents(view: View) {
        mPresenter =  ChatPresenter(this)
        mPresenter.getChats(mSharedPrefManager.authToken!!)
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            mPresenter.getChats(mSharedPrefManager.authToken!!)
        }
        imvOthers.setOnClickListener {
            SecretMessagesActivity.startActivity(mContext as AppCompatActivity)
        }
    }

    override fun onResponseSuccess(data: ChatsResponse) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            if (data.data.isNullOrEmpty()) {
                noData.visibility = View.VISIBLE
                rvMessages.visibility = View.GONE
                noData.tvNoData.text = getString(R.string.no_chats)
            } else {
                noData.visibility = View.GONE
                rvMessages.visibility = View.VISIBLE
                rvMessages.setItemViewCacheSize(data.data.size)
                rvMessages.adapter = ChatsAdapter(mContext, data.data, R.layout.recycler_item_messages, this)

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

    fun hasPermissions(context: Context?,  permissions: Array<String>?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission!!) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }
    override fun onChatClick(position: Int, model: ChatsModel) {
        val PERMISSION_ALL = 1
        val PERMISSIONS = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        )

        if (!hasPermissions(mContext, PERMISSIONS)) {
            ActivityCompat.requestPermissions(mContext as Activity, PERMISSIONS, PERMISSION_ALL)
            val  permission = object : PermissionManager() {

            }
            permission.checkAndRequestPermissions(mContext as Activity)
        }else{
            ChatDetailsActivity.startActivity(mContext as AppCompatActivity, model.other_user_data.id)
        }

    }

}