package com.app.phew.ui.phewPremiumMemberShip

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.packages.PackagesModel
import com.app.phew.models.packages.PackagesResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_phew_premium_member_ship.*
import kotlinx.android.synthetic.main.no_data_layout.view.*

class PhewPremiumMemberShipActivity : ParentActivity() , PhewPremiumMemberShipContract.View, PackagesAdapter.OnPackageClick {
    override val layoutResource: Int
        get() = R.layout.activity_phew_premium_member_ship
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
            appCompatActivity.startActivity(Intent(appCompatActivity, PhewPremiumMemberShipActivity::class.java))
        }
    }



    private lateinit var mPresenter: PhewPremiumMemberShipPresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = PhewPremiumMemberShipPresenter(this)
        mPresenter.getPackages()
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            mPresenter.getPackages()
        }
        btnSubScribeNow.setOnClickListener {
            if (packgeId>=0){
                mPresenter.subscribeToPackage(mSharedPrefManager.authToken!!,packgeId)
            }
        }
    }




    override fun subscribeToSuccess(response: BaseResponse) {
        CommonUtil.makeToast(this,response.message?:"")
        finish()
    }


    private var packagesList:ArrayList<PackagesModel> = ArrayList()
    override fun onResponseSuccess(data: PackagesResponse) {
        swipeRefresh.isRefreshing = false
        if (data.data.isNullOrEmpty()) {
            noData.visibility = View.VISIBLE
            rvPackages.visibility = View.GONE
            noData.tvNoData.text = getString(R.string.no_packages)
        } else {
            noData.visibility = View.GONE
            rvPackages.visibility = View.VISIBLE
            packagesList = data.data
            rvPackages.setItemViewCacheSize(packagesList.size)
            rvPackages.adapter = PackagesAdapter(mContext, packagesList, R.layout.recycler_item_package, this)

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

    private var packgeId = -1
    override fun onPackageClick(position: Int, model: PackagesModel) {

        packgeId = model.id
        for (i in packagesList){
            i.isSelected = false
        }
        packagesList[position].isSelected = true
        rvPackages.adapter?.notifyDataSetChanged()
    }
}