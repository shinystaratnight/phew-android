package com.app.phew.ui.dashboard

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.phew.R
import com.app.phew.base.BaseFragment
import com.app.phew.listners.OnItemClickListener
import com.app.phew.models.BaseResponse
import com.app.phew.models.ListModel
import com.app.phew.models.findly.contries.FindlyCountriesResponse
import com.app.phew.models.findly.findlyCities.FindlyCitiesResponse
import com.app.phew.models.findly.findlyCities.FindlyCity
import com.app.phew.ui.mostInterActiveCitiesPosts.MostInterActiveCitiesPostsActivity
import com.app.phew.ui.secretMessages.SecretMessagesActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.app.phew.utils.views.ListDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_findly.*
import kotlinx.android.synthetic.main.no_data_layout.view.*
import java.util.ArrayList

class FindlyFragment : BaseFragment(), FindlyContract.View, FindlyCitiesAdapter.OnCityClick, OnItemClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_findly
    override val isRecycle: Boolean
        get() = false

    lateinit var mPresenter: FindlyPresenter
    private var countryId = -1
    private var cityId = -1
    lateinit var models: ArrayList<ListModel>
    lateinit var mDialog: ListDialog
    override fun initializeComponents(view: View) {
        mPresenter =  FindlyPresenter(this)
        models = ArrayList()
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            if (countryId>0){
                mPresenter.getFindlyCities(countryId)
            }else{
                swipeRefresh.isRefreshing  = false
            }

        }
        tvSelectCountries.setOnClickListener {
            mPresenter.getFindlyCountries()
        }

    }

    override fun getFindlyCitiesSuccess(response: FindlyCitiesResponse) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            if (response.data.isNullOrEmpty()) {
                noData.visibility = View.VISIBLE
                rvFindlyCities.visibility = View.GONE
                noData.tvNoData.text = getString(R.string.there_are_no_cities_currently_available)
            } else {

                noData.visibility = View.GONE
                rvFindlyCities.visibility = View.VISIBLE
                rvFindlyCities.setItemViewCacheSize(response.data.size)
                rvFindlyCities.adapter = FindlyCitiesAdapter(mContext, response.data, R.layout.recycler_item_findly_countries, this)

            }
        }
    }

    override fun onResponseSuccess(data: FindlyCountriesResponse) {
        if (activity != null && isAdded) {
            swipeRefresh.isRefreshing = false
            val dataList: ArrayList<ListModel> = ArrayList()
            for (i in data.data.indices) {
                dataList.add(
                        ListModel(
                                data.data[i].id,
                                data.data[i].name
                        )
                )
            }
            models.clear()
            models.addAll(dataList)
            mDialog = ListDialog(mContext, this, models, getString(R.string.select_country))
            mDialog.show()

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

    override fun onCityClick(position: Int, model: FindlyCity) {
        cityId = model.id
        if (countryId>0&&cityId>0){
            val url ="findly/countries/$countryId/cities/$cityId"
            MostInterActiveCitiesPostsActivity.startActivity(mContext as AppCompatActivity,url)
        }else{

        }
    }

    override fun onItemClick(view: View, position: Int) {
        mDialog.dismiss()
        tvSelectCountries.text = models[position].name
                countryId=models[position].id?:0
        mPresenter.getFindlyCities(countryId)
    }

}