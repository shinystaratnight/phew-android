package com.app.phew.ui.home

import android.app.Dialog
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.akexorcist.screenshotdetection.ScreenshotDetectionDelegate
import com.app.phew.R
import com.app.phew.base.BaseFragment
import com.app.phew.listners.PaginationListener
import com.app.phew.listners.PaginationListener.Companion.PAGE_START
import com.app.phew.models.BaseResponse
import com.app.phew.models.home.HomeModel
import com.app.phew.models.home.HomeResponse
import com.app.phew.models.home.ScreenShotBody
import com.app.phew.models.images.ImageModel
import com.app.phew.network.Urls
import com.app.phew.ui.createPost.CreatePostActivity
import com.app.phew.ui.createActivity.MoviesActivity
import com.app.phew.ui.createActivity.PlacesActivity
import com.app.phew.ui.postDetails.PhotosAdapter
import com.app.phew.ui.postDetails.PostDetailsActivity
import com.app.phew.ui.showProfile.ShowProfileActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.bottom_sheet_post_viewers.*
import kotlinx.android.synthetic.main.dialog_show_post.*
import kotlinx.android.synthetic.main.dialog_show_post.ibChatImageClose
import kotlinx.android.synthetic.main.dialog_show_video.*

import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment(private var flag: String, private var url: String) : BaseFragment(),
    HomeContract.View,
    SwipeRefreshLayout.OnRefreshListener, HomeAdapter.HomeItemClickListeners,
    ScreenshotDetectionDelegate.ScreenshotDetectionListener, PhotosAdapter.OnPhotoClick,
    Player.EventListener {
    override val layoutResource: Int
        get() = R.layout.fragment_home
    override val isRecycle: Boolean
        get() = false

    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null
    private var fabClock: Animation? = null
    private var fabAntiClock: Animation? = null
    private var isOpen = false
    private lateinit var mPresenter: HomePresenter

    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: HomeAdapter
    private lateinit var mHomeItems: ArrayList<HomeModel>

    private var currentPage = PAGE_START
    private var mType = "all"
    private var isLastPage = false
    private val totalPage = 10
    private var isLoading = false
    private var postsItemActionType: String? = null

    private lateinit var mScreenshotDetectionDelegate: ScreenshotDetectionDelegate

    override fun initializeComponents(view: View) {
        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        fabClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock)
        fabAntiClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock)
        if (flag == "main") {
            linHomeOptions.visibility = View.VISIBLE
            fabMenu.visibility = View.VISIBLE
        } else {
            mType = ""
            linHomeOptions.visibility = View.GONE
            fabMenu.visibility = View.GONE
        }
        setHasOptionsMenu(true)

        fabHomeCreate.setOnClickListener { fabClickListener() }

        srHome.setOnRefreshListener(this)
        mLayoutManager = LinearLayoutManager(mContext)
        mHomeItems = ArrayList()
        mAdapter = HomeAdapter(mContext, mHomeItems, this)
        //rvHome.setHasFixedSize(true)
        rvHome.setItemViewCacheSize(mHomeItems.size)
        rvHome.layoutManager = mLayoutManager
        rvHome.adapter = mAdapter
        mPresenter = HomePresenter(this)
        rvHome.addOnScrollListener(object : PaginationListener(mLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                mPresenter.getHome(url, mSharedPrefManager.authToken.toString(), mType, currentPage)
            }

            override fun scrollToDown() {
                linHomeOptions.visibility = View.GONE
            }

            override fun scrollToUp() {
                linHomeOptions.visibility = View.VISIBLE
            }

            override fun isLastPage() = isLastPage
            override fun isLoading() = isLoading
        })

        ibHomeAll.setOnClickListener {
            currentPage = PAGE_START
            mType = "all"
            mPresenter.getHome(url, mSharedPrefManager.authToken.toString(), mType, currentPage)

            ibHomeAll.visibility = View.GONE
            btnHomeAll.visibility = View.VISIBLE
            val timer = object : CountDownTimer(2000, 200) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    ibHomeAll.visibility = View.VISIBLE
                    btnHomeAll.visibility = View.GONE
                }
            }
            timer.start()
        }
        ibHomeFriends.setOnClickListener {
            currentPage = PAGE_START
            mType = "friends"
            mPresenter.getHome(url, mSharedPrefManager.authToken.toString(), mType, currentPage)

            ibHomeFriends.visibility = View.GONE
            btnHomeFriends.visibility = View.VISIBLE
            val timer = object : CountDownTimer(2000, 200) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    ibHomeFriends.visibility = View.VISIBLE
                    btnHomeFriends.visibility = View.GONE
                }
            }
            timer.start()
        }

        mScreenshotDetectionDelegate = ScreenshotDetectionDelegate(requireActivity(), this)

    }

    override fun onResume() {
        if (activity != null && isAdded) {
            super.onResume()
            currentPage = PAGE_START
            isLastPage = false
            mAdapter.clear()
            mPresenter.getHome(url, mSharedPrefManager.authToken.toString(), mType, currentPage)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (activity != null && isAdded) {
            if (flag == "main") {
                inflater.inflate(R.menu.home_menu, menu)
                super.onCreateOptionsMenu(menu, inflater)
            }
        }
    }

    private fun fabClickListener() {
        if (activity != null && isAdded) {
            isOpen = if (isOpen) {
                fabHomeCreatePost.startAnimation(fabClose)
                fabHomeCreateWatching.startAnimation(fabClose)
                fabHomeCreateLocation.startAnimation(fabClose)
                fabHomeCreate.startAnimation(fabAntiClock)
                false
            } else {
                fabHomeCreatePost.startAnimation(fabOpen)
                fabHomeCreateWatching.startAnimation(fabOpen)
                fabHomeCreateLocation.startAnimation(fabOpen)
                fabHomeCreate.startAnimation(fabClock)
                true
            }
        }

        fabHomeCreatePost.setOnClickListener {
            CreatePostActivity.startActivity(mContext as AppCompatActivity, Urls.POSTS)
        }

        fabHomeCreateWatching.setOnClickListener {
            MoviesActivity.startActivity(mContext as AppCompatActivity)
        }
        fabHomeCreateLocation.setOnClickListener {
            PlacesActivity.startActivity(mContext as AppCompatActivity)
        }
    }

    override fun onPostUpdate(response: BaseResponse) {
        if (activity != null && isAdded) {
            isFirstScreen = true
            if (deletedItem != null) mAdapter.removeItem(deletedItem!!)
            if (reactedItemPos != null) {
                when (reactPosition) {
                    -1 -> {
                    }
                    0 -> {
                        mAdapter.getItem(reactedItemPos ?: 0).data?.is_like = false
                        mAdapter.getItem(reactedItemPos ?: 0).data?.like_type = ""
                        mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count =
                            mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count
                                ?: 1.minus(1)
                    }
                    1 -> {
                        mAdapter.getItem(reactedItemPos ?: 0).data?.is_like = true
                        mAdapter.getItem(reactedItemPos ?: 0).data?.like_type = "love"
                        mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count =
                            mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count ?: 0.plus(1)
                    }
                    2 -> {
                        mAdapter.getItem(reactedItemPos ?: 0).data?.is_like = true
                        mAdapter.getItem(reactedItemPos ?: 0).data?.like_type = "laugh"
                        mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count =
                            mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count ?: 0.plus(1)
                    }
                    3 -> {
                        mAdapter.getItem(reactedItemPos ?: 0).data?.is_like = true
                        mAdapter.getItem(reactedItemPos ?: 0).data?.like_type = "dislike"
                        mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count =
                            mAdapter.getItem(reactedItemPos ?: 0).data?.likes_count ?: 0.plus(1)
                    }
                    else -> {
                    }
                }
                mAdapter.notifyItemChanged(reactedItemPos ?: 0)
            }
            if (postsItemActionType == "Fav") {
                mAdapter.getItem(reactedItemPos ?: 0).data?.is_fav = !mAdapter.getItem(reactedItemPos ?: 0).data?.is_fav!!
                mAdapter.notifyItemChanged(reactedItemPos ?: 0)
                postsItemActionType = null
            }
        }
    }

    override fun onResponseSuccess(data: HomeResponse) {
        if (activity != null && isAdded) {
            if (currentPage != PAGE_START) mAdapter.removeLoading()
            mHomeItems = data.data
            mAdapter.addItems(mHomeItems)
            srHome.isRefreshing = false
            if (currentPage < totalPage) {
                mAdapter.addLoading()
            } else {
                isLastPage = true
            }
            isLoading = false
            rvHome.setItemViewCacheSize(mHomeItems.size)
            if (data.data.isEmpty()) {
                srHome.isRefreshing = false
                hideProgress()
                mAdapter.removeLoading()
            }
        }
    }

    override fun onResponseError(errorBody: String) {
        if (activity != null && isAdded) {
            isFirstScreen = true
            SBManager.displayError(
                mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message.toString()
            )
        }
    }

    override fun onResponseFailure(t: Throwable) {
        if (activity != null && isAdded) {
            isFirstScreen = true
            CommonUtil.handleException(mContext, t)
        }
    }

    override fun showProgress() {
        if (activity != null && isAdded) {
            relLoader.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        if (activity != null && isAdded) {
            relLoader.visibility = View.GONE
            srHome.isRefreshing = false
        }
    }

    override fun onRefresh() {
        if (activity != null && isAdded) {
            currentPage = PAGE_START
            isLastPage = false
            mAdapter.clear()
            mPresenter.getHome(url, mSharedPrefManager.authToken.toString(), mType, currentPage)
        }
    }

    override fun onPostClicked(position: Int) {
       PostDetailsActivity.startActivity(mContext as AppCompatActivity, mHomeItems[position])
    }

    private var deletedItem: HomeModel? = null
    override fun onDeleteClick(position: Int, postId: Int) {
        if (activity != null && isAdded) {
            mPresenter.deletePost(mSharedPrefManager.authToken.toString(), postId)
            deletedItem = mHomeItems[position]
        }
    }

    override fun onFindlayClick(postId: Int) {
        if (activity != null && isAdded) {
            mPresenter.findlayPost(mSharedPrefManager.authToken.toString(), postId)
        }
    }

    override fun onUpdatePrivacyClick(postId: Int) {
        if (activity != null && isAdded) showPostViewers(postId)
    }

    private fun showPostViewers(postId: Int) {
        val postViewersSheet = RoundedBottomSheetDialog(mContext)
        postViewersSheet.setContentView(
            LayoutInflater.from(mContext)
                .inflate(R.layout.bottom_sheet_post_viewers, null, false)
        )
        postViewersSheet.rgPostViewers.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbPostViewersAll -> mPresenter.updatePostPrivacy(
                    mSharedPrefManager.authToken.toString(), postId, "all"
                )
                R.id.rbPostViewersFriends -> mPresenter.updatePostPrivacy(
                    mSharedPrefManager.authToken.toString(), postId, "friends"
                )
                R.id.rbPostViewersFollowers -> mPresenter.updatePostPrivacy(
                    mSharedPrefManager.authToken.toString(), postId, "followers"
                )
                R.id.rbPostViewersOnlyMe -> mPresenter.updatePostPrivacy(
                    mSharedPrefManager.authToken.toString(), postId, "only_me"
                )
            }
            postViewersSheet.dismiss()
        }
        postViewersSheet.show()
    }

    override fun onFavoriteClick(postId: Int) {
        if (activity != null && isAdded) {
            postsItemActionType = "Fav"
            reactedItemPos = mHomeItems.indexOf(mHomeItems.find { it.data?.id == postId })
            mPresenter.setPostFavorite(mSharedPrefManager.authToken.toString(), postId)
        }
    }

    private var reactedItemPos: Int? = null
    private var reactPosition = -1
    override fun onReactClick(postId: Int, reactionPosition: Int) {
        reactedItemPos = mHomeItems.indexOf(mHomeItems.find { it.data?.id == postId })
        reactPosition = reactionPosition
        when (reactionPosition) {
            -1 -> {
            }
            0 -> mPresenter.reactPost(mSharedPrefManager.authToken.toString(), postId, "")
            1 -> mPresenter.reactPost(mSharedPrefManager.authToken.toString(), postId, "love")
            2 -> mPresenter.reactPost(mSharedPrefManager.authToken.toString(), postId, "laugh")
            3 -> mPresenter.reactPost(mSharedPrefManager.authToken.toString(), postId, "dislike")
            else -> {
            }
        }
    }

    override fun onUserClick(userId: Int) {
        ShowProfileActivity.startActivity(mContext as AppCompatActivity, userId)
    }

    override fun onPhotosClick(list: ArrayList<ImageModel>) {
        showImageDialog(list)
    }


    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(mContext, "exoplayer-sample")
    }
    private lateinit var simpleExoplayer: SimpleExoPlayer
    val dialog: Dialog? = null
    var showVideo = false
    private var playbackPosition: Long = 0
    private fun showImageVideoDialog(file: String) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_show_video)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        showVideo = true
        initializePlayer(file)
        dialog.ibChatImageClose.setOnClickListener {
            dialog.dismiss()
            releasePlayer()
        }
    }


    private fun initializePlayer(videoUrl: String) {
        if (showVideo) {
            simpleExoplayer = SimpleExoPlayer.Builder(mContext).build()
            preparePlayer(videoUrl)
            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_show_video)
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.video_view.visibility = View.VISIBLE
            showVideo = true
            dialog.ibChatImageClose.setOnClickListener {
                dialog.dismiss()
                releasePlayer()
            }
            dialog.video_view?.player = simpleExoplayer
            dialog.video_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            simpleExoplayer.seekTo(playbackPosition)
            simpleExoplayer.playWhenReady = true
            simpleExoplayer.addListener(this)
            simpleExoplayer.videoScalingMode = Renderer.VIDEO_SCALING_MODE_DEFAULT
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setCancelable(false)
            dialog.show()
        } else {
            Log.e("ffff", "ffff")
        }

    }


    private fun buildMediaSource(uri: Uri): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }


    private fun preparePlayer(videoUrl: String) {
        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource)
    }

    private fun releasePlayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    override fun onPlayerError(error: ExoPlaybackException) {
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        /*  if (playbackState == Player.STATE_BUFFERING)
              progressBar.visibility = View.VISIBLE
          else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED)
              progressBar.visibility = View.INVISIBLE*/
    }


    private fun showImageDialog(images: ArrayList<ImageModel>) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_show_post)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.ibChatImageClose.setOnClickListener { dialog.dismiss() }
        dialog.rvImages.adapter =
            PhotosAdapter(mContext, images, R.layout.item_recycler_show_images, this)

        dialog.show()
    }

    override fun onStart() {
        if (activity != null && isAdded) {
            super.onStart()
            mScreenshotDetectionDelegate.startScreenshotDetection()
        }
    }

    override fun onStop() {
        super.onStop()
        if (showVideo) {
            releasePlayer()
            dialog?.dismiss()
        }
        mScreenshotDetectionDelegate.stopScreenshotDetection()
    }

    private var isFirstScreen = true
    override fun onScreenCaptured(path: String?) {
        if (activity != null && isAdded) {
            Log.e("SCREEN", "ScreenCaptured")

            if (isFirstScreen) {
                isFirstScreen = false
                val layoutManager = rvHome.layoutManager as LinearLayoutManager
                val startPosition = layoutManager.findFirstVisibleItemPosition()
                val endPosition = layoutManager.findLastVisibleItemPosition()
                val posts = ArrayList<String>()
                if (startPosition != endPosition) {
                    for (i in startPosition..endPosition)
                        posts.add("${mAdapter.getItems()[i].data?.id}")
                } else posts.add("${mAdapter.getItems()[startPosition].data?.id}")

                mPresenter.screenShot(
                    mSharedPrefManager.authToken.toString(), ScreenShotBody(posts.toString())
                )
            }
        }
    }

    override fun onScreenCapturedWithDeniedPermission() {
        if (activity != null && isAdded) {
            Log.e("SCREEN", "ScreenCapturedWithNoPermission")

            if (isFirstScreen) {
                isFirstScreen = false
                val layoutManager = rvHome.layoutManager as LinearLayoutManager
                val startPosition = layoutManager.findFirstVisibleItemPosition()
                val endPosition = layoutManager.findLastVisibleItemPosition()
                val posts = ArrayList<String>()
                if (startPosition != endPosition) {
                    for (i in startPosition..endPosition)
                        posts.add("${mAdapter.getItems()[i].data?.id}")
                } else posts.add("${mAdapter.getItems()[startPosition].data?.id}")

                mPresenter.screenShot(
                    mSharedPrefManager.authToken.toString(), ScreenShotBody(posts.toString())
                )
            }
        }
    }

    override fun onPhotoClick(position: Int, model: ImageModel) {
        showImageVideoDialog(model.data!!)
    }
}