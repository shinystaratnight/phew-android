package com.app.phew.ui.secretMessages

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.secretMessages.SecretMessageModel
import com.app.phew.models.secretMessages.SecretMessageResponse
import com.app.phew.ui.createPost.CreatePostActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_secret_messages.*
import kotlinx.android.synthetic.main.no_data_layout.view.*

class SecretMessagesActivity : ParentActivity() , SecretMessagesContract.View,
    SecretMessagesAdapter.OnSecretMessageClickListener {
    override val layoutResource: Int
        get() = R.layout.activity_secret_messages
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
            appCompatActivity.startActivity(Intent(appCompatActivity, SecretMessagesActivity::class.java))
        }
    }



    private lateinit var mPresenter: SecretMessagesPresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = SecretMessagesPresenter(this)
        mPresenter.getSecretMessages(mSharedPrefManager.authToken!!)
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            mPresenter.getSecretMessages(mSharedPrefManager.authToken!!)
        }
        setToolbarTitle(getString(R.string.secret_messages))
    }




    override fun onResponseSuccess(data: SecretMessageResponse) {
        swipeRefresh.isRefreshing = false
        if (data.data.isNullOrEmpty()) {
            noData.visibility = View.VISIBLE
            rvMessages.visibility = View.GONE
            noData.tvNoData.text = getString(R.string.no_chats)
        } else {
            noData.visibility = View.GONE
            rvMessages.visibility = View.VISIBLE
            rvMessages.setItemViewCacheSize(data.data.size)
            rvMessages.adapter = SecretMessagesAdapter(mContext, data.data, R.layout.recycler_item_secret_messages, this)

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

    override fun onSecretMessageClick(position: Int, model: SecretMessageModel) {
        CreatePostActivity.startActivity(this,"secret_message/${model.id}")
        finish()
    }
}