package com.app.phew.ui.createActivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.friends.FriendModel
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.movies.MovieDetail
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.models.places.MapsSearchData
import com.app.phew.models.places.PlacesResponse
import com.app.phew.ui.main.MainActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_activity.*
import kotlinx.android.synthetic.main.bottom_sheet_post_tags.*
import kotlinx.android.synthetic.main.bottom_sheet_post_viewers.*

class CreateActivityActivity : ParentActivity(), CreateActivityContract.View,
    FriendsAdapter.onFriendCheckedChangeListener, FriendNameAdapter.onFriendNameDeleteListener {
    override val layoutResource: Int
        get() = R.layout.activity_create_activity
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
        fun startActivity(appCompatActivity: AppCompatActivity, isLocation: Boolean, activityData: String) {
            appCompatActivity.startActivity(
                    Intent(appCompatActivity, CreateActivityActivity::class.java)
                            .putExtra("is_location", isLocation).putExtra("activity_data", activityData)
            )
        }
    }

    private lateinit var mPresenter: CreateActivityPresenter
    private var mIsLocation = false
    private var mSelectedMovie: MovieDetail? = null
    private var mSelectedLocation: MapsSearchData? = null
    private var mShowPrivacy = "all"
    private var isTags = false
    private lateinit var mPostTagsSheet: RoundedBottomSheetDialog
    private lateinit var mSelectedFriends : ArrayList<FriendModel>

    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        mPresenter = CreateActivityPresenter(this)
        mIsLocation = intent.getBooleanExtra("is_location", false)
        if (mIsLocation)
            mSelectedLocation = Gson().fromJson(intent.getStringExtra("activity_data"), MapsSearchData::class.java)
        else mSelectedMovie = Gson().fromJson(intent.getStringExtra("activity_data"), MovieDetail::class.java)

        Glide.with(this).load(mSharedPrefManager.userData.profile_image).into(ivCreateActivityProfile)

        tvCreateActivityUser.text = mSharedPrefManager.userData.fullname
        if (mIsLocation) {
            tvCreateActivityActivity.text = getString(R.string.`in`)
            tvCreateActivityName.text = mSelectedLocation?.name.toString()
            ibCreateActivityActivity.setImageResource(R.drawable.ic_location_h)
        } else {
            tvCreateActivityActivity.text = getString(R.string.watching)
            tvCreateActivityName.text = mSelectedMovie?.title.toString()
            ibCreateActivityActivity.setImageResource(R.drawable.ic_watching_h)
        }

        ibCreateActivityOptionsTag.setOnClickListener { showPostTags() }

        ibCreateActivityOptionsViewers.setOnClickListener { showPostViewers() }

        tvCreateActivity.setOnClickListener {
            var friendsWith: String? = null
            if (mSelectedFriends.size > 0) friendsWith = mSelectedFriends.map { model -> "\"" + model.user?.id.toString() + "\"" }.joinToString(prefix = "[", postfix = "]")
            if (mIsLocation)
                mPresenter.createPost(mSharedPrefManager.authToken.toString(), null, "first", "location",
                        null, Gson().toJson(mSelectedLocation), null, friendsWith, mShowPrivacy, 0, null, null)
            else mPresenter.createPost(mSharedPrefManager.authToken.toString(), null, "first", "watching",
                    null, null, Gson().toJson(mSelectedMovie), friendsWith, mShowPrivacy, 0, null, null)
        }

        mSelectedFriends = ArrayList()
    }

    private fun showPostTags() {
        mPostTagsSheet = RoundedBottomSheetDialog(mContext)
        mPostTagsSheet.setContentView(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.bottom_sheet_post_tags, null, false)
        )

        mPostTagsSheet.btnPostTagsConfirm.setOnClickListener {
            if (mSelectedFriends.size > 0) {
                rvCreateActivityFriendsView.adapter = FriendNameAdapter(mContext, mSelectedFriends, this)
                mPostTagsSheet.dismiss()
            }
        }

        isTags = true
        mPresenter.getFriends(mSharedPrefManager.authToken.toString(),mSharedPrefManager.userData.id!!)

        mPostTagsSheet.show()
    }

    private fun showPostViewers() {
        val postViewersSheet = RoundedBottomSheetDialog(mContext)
        postViewersSheet.setContentView(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.bottom_sheet_post_viewers, null, false)
        )
        postViewersSheet.rgPostViewers.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbPostViewersAll -> mShowPrivacy = "all"
                R.id.rbPostViewersFriends -> mShowPrivacy = "friends"
                R.id.rbPostViewersFollowers -> mShowPrivacy = "followers"
                R.id.rbPostViewersOnlyMe -> mShowPrivacy = "only_me"
            }
            postViewersSheet.dismiss()
        }
        postViewersSheet.show()
    }

    override fun onGetFriends(data: FriendsResponse) {
        if (!data.data.isNullOrEmpty())
            mPostTagsSheet.rvPostTags.adapter = FriendsAdapter(mContext, data.data, this)
    }

    override fun onCreatePost(data: PostResponse) {
        MainActivity.startActivity(mContext as AppCompatActivity)
        finishAffinity()
    }

    override fun onSearchSuccess(data: MoviesSearchResponse) {}
    override fun onSearchPlaces(data: PlacesResponse) {}
    override fun onResponseSuccess(data: MoviesResponse) {}

    override fun onResponseError(errorBody: String) {
        SBManager.displayError(
                mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message.toString()
        )
    }

    override fun onResponseFailure(t: Throwable) {
        CommonUtil.handleException(mContext, t)
    }

    override fun showProgress() {
        if (isTags) mPostTagsSheet.relTagsLoader.visibility = View.VISIBLE
        else relLoader.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        if (isTags) mPostTagsSheet.relTagsLoader.visibility = View.GONE
        else relLoader.visibility = View.GONE
    }

    override fun onFriendClick(position: Int, checkedStatus: Boolean, model: FriendModel) {
        if (checkedStatus) mSelectedFriends.add(model)
        else mSelectedFriends.remove(model)
    }

    override fun onFriendDelete(position: Int, model: FriendModel) {
        mSelectedFriends.remove(model)
        rvCreateActivityFriendsView.adapter?.notifyItemRemoved(position)
    }
}