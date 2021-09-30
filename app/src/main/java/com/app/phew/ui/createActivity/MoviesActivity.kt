package com.app.phew.ui.createActivity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.movies.MovieDetail
import com.app.phew.models.movies.MovieModel
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.models.places.PlacesResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : ParentActivity(), CreateActivityContract.View,
        MoviesAdapter.OnMovieClickListener, MoviesSearchAdapter.OnMovieSearchClickListener {
    override val layoutResource: Int
        get() = R.layout.activity_movies
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
            appCompatActivity.startActivity(Intent(appCompatActivity, MoviesActivity::class.java))
        }
    }

    private lateinit var mPresenter: CreateActivityPresenter
    private lateinit var mMovies: ArrayList<MovieModel>
    private lateinit var mMoviesSearch: ArrayList<MovieDetail>

    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        mPresenter = CreateActivityPresenter(this)
        mPresenter.getMovies()
        mMovies = ArrayList()
        mMoviesSearch = ArrayList()

        etMoviesSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!v?.text.isNullOrEmpty()) mPresenter.searchMovies(query = v?.text.toString(), page = 1)
                    return true
                }
                return false
            }
        })

        etMoviesSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) mPresenter.getMovies()
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) mPresenter.getMovies()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
    }

    override fun onResponseSuccess(data: MoviesResponse) {
        if (!data.data.isNullOrEmpty()) {
            mMovies = ArrayList()
            mMovies = data.data
            rvMovies.adapter = MoviesAdapter(mContext, mMovies, this)
        }
    }

    override fun onSearchSuccess(data: MoviesSearchResponse) {
        if (!data.results.isNullOrEmpty()) {
            mMoviesSearch = ArrayList()
            mMoviesSearch = data.results
            rvMovies.adapter = MoviesSearchAdapter(mContext, mMoviesSearch, this)
        }
    }

    override fun onSearchPlaces(data: PlacesResponse) {}
    override fun onGetFriends(data: FriendsResponse) {}
    override fun onCreatePost(data: PostResponse) {}

    override fun onResponseError(errorBody: String) {
        SBManager.displayError(
                mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message.toString()
        )
    }

    override fun onResponseFailure(t: Throwable) {
        CommonUtil.handleException(mContext, t)
    }

    override fun showProgress() {
        relLoader.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        relLoader.visibility = View.GONE
    }

    override fun onMovieClicked(position: Int) {
        CreateActivityActivity.startActivity(mContext as AppCompatActivity, false, mMovies[position].movie_data.toString())
    }

    override fun onMovieSearchClicked(position: Int) {
        CreateActivityActivity.startActivity(mContext as AppCompatActivity, false, Gson().toJson(mMoviesSearch[position]))
    }
}