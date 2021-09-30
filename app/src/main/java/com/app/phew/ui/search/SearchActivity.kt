package com.app.phew.ui.search

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.listners.OnItemClickListener
import com.app.phew.models.BaseResponse
import com.app.phew.models.searchResponse.SearchModel
import com.app.phew.models.searchResponse.SearchResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.no_data_layout.*


class SearchActivity : ParentActivity(), SearchContract.View {
    override val layoutResource: Int
        get() = R.layout.activity_search
    override val isEnableToolbar: Boolean
        get() = true
    override val isFullScreen: Boolean
        get() = false
    override val isEnableBack: Boolean
        get() = true
    override val isRecycle: Boolean
        get() = false

    override fun hideInputType() = true

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity) {
            appCompatActivity.startActivity(
                Intent(appCompatActivity, SearchActivity::class.java)
            )
        }
    }


    private var userList: ArrayList<SearchModel> = ArrayList()
    lateinit var mPresenter: SearchPresenter
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setToolbarTitle(getString(R.string.search))
        mPresenter = SearchPresenter(this)
        etSearch.requestFocus()
        swipeRefresh.setColorSchemeColors(Color.parseColor("#00ADF1"))
        swipeRefresh.setOnRefreshListener {
            userList.clear()
            if (etSearch.text.toString().isNotEmpty()) {
                mPresenter.search(
                        etSearch.text.toString())
            }else{
                swipeRefresh.isRefreshing = false
            }

        }
        imvClear.setOnClickListener {
            etSearch.setText("")
            imvClear.visibility = View.GONE
        }
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    imvClear.visibility = View.VISIBLE
                    mPresenter.search(
                            etSearch.text.toString())
                }else{
                    imvClear.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mPresenter.search(
                        etSearch.text.toString())
                val `in` = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                `in`.hideSoftInputFromWindow(etSearch.windowToken, 0)
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onResponseSuccess(data: SearchResponse) {
        swipeRefresh.isRefreshing = false

        noData.visibility = View.INVISIBLE
        if (data.data.isEmpty()) {
            noData.visibility = View.VISIBLE
            rvSearch.visibility = View.INVISIBLE
            tvNoData.text = getString(R.string.no_user)
        } else {
            noData.visibility = View.INVISIBLE
            userList.addAll(data.data)
            rvSearch.visibility = View.VISIBLE
            val searchAdapter = SearchProductsAdapter(
                mContext,
                userList,
                object : OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                       /* ProductDetailsActivity.startActivity(
                            mContext as AppCompatActivity,
                            data.data[position].id,
                            "search"
                        )*/
                        finish()
                    }
                }
            )
            rvSearch.adapter = searchAdapter
        }


    }

    override fun onResponseError(errorBody: String) {
        swipeRefresh.isRefreshing = false
        SBManager.displayError(
            mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message ?: ""
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
}