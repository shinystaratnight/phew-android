package com.app.phew.ui.postDetails

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.comment.CommentModel
import com.app.phew.models.comment.CommentResponse
import com.app.phew.models.home.HomeModel
import com.app.phew.models.images.ImageModel
import com.app.phew.ui.home.HomeAdapter
import com.app.phew.ui.showProfile.ShowProfileActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.esafirm.imagepicker.features.ImagePicker
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
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.android.synthetic.main.bottom_echo_options.*
import kotlinx.android.synthetic.main.bottom_sheet_post_viewers.*
import kotlinx.android.synthetic.main.dialog_show_post.*
import kotlinx.android.synthetic.main.dialog_show_post.ibChatImageClose
import kotlinx.android.synthetic.main.dialog_show_video.*

class PostDetailsActivity: ParentActivity(),PostDetailsContract.View,
    HomeAdapter.HomeItemClickListeners, CommentsAdapter.OnCommentClick, Player.EventListener, PhotosAdapter.OnPhotoClick {
    override val layoutResource: Int
        get() = R.layout.activity_post_details
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
        fun startActivity(appCompatActivity: AppCompatActivity,post:HomeModel) {
            appCompatActivity.
            startActivity(Intent(appCompatActivity, PostDetailsActivity::class.java).putExtra("post",post))
        }
    }



    private lateinit var mPresenter:PostDetailsPresenter
    private var postModel:HomeModel?=null
    private lateinit var mAdapter: HomeAdapter

    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        postModel = intent?.extras?.getSerializable("post") as HomeModel
        mPresenter = PostDetailsPresenter(this)
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        mPresenter.getComments(mSharedPrefManager.authToken!!,postModel!!.data!!.id)
        swipeRefresh.setOnRefreshListener {
            mPresenter.getComments(mSharedPrefManager.authToken!!,postModel!!.data!!.id)
        }
        setToolbarTitle(getString(R.string.friend_requests))
        val post:ArrayList<HomeModel> = ArrayList()
        post.add(postModel!!)
        mAdapter = HomeAdapter(mContext, post, this)
        rvHome.adapter = mAdapter
        tvSend.setOnClickListener {
            if (etComment.text.toString().trim().isNotEmpty()){
                mPresenter.addComment(
                    mSharedPrefManager.authToken!!,postModel!!.data!!.id,etComment.text.toString().trim(),null
                )
            }
        }
        imvAddMedia.setOnClickListener {
            pickImages()
        }
       // showImageVideoDialog("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
    }


    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(this, "exoplayer-sample")
    }
    private lateinit var simpleExoplayer: SimpleExoPlayer
    val dialog : Dialog?=null
    var showVideo = false
    private var playbackPosition: Long = 0
    private fun showImageVideoDialog(file: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_show_video)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
            showVideo = true
            initializePlayer(file)
            dialog.ibChatImageClose.setOnClickListener { dialog.dismiss()
                releasePlayer()
            }
    }


    private fun initializePlayer(videoUrl: String) {
        if (showVideo){
            simpleExoplayer = SimpleExoPlayer.Builder(this).build()
            preparePlayer(videoUrl)
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_show_video)
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.video_view.visibility = View.VISIBLE
            showVideo = true
            dialog.ibChatImageClose.setOnClickListener { dialog.dismiss()
                releasePlayer() }
            dialog.video_view?.player = simpleExoplayer
            dialog.video_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            simpleExoplayer.seekTo(playbackPosition)
            simpleExoplayer.playWhenReady = true
            simpleExoplayer.addListener(this)
            simpleExoplayer.videoScalingMode = Renderer.VIDEO_SCALING_MODE_DEFAULT
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setCancelable(false)
            dialog.show()
        }else{
            Log.e("ffff","ffff")
        }

    }




    override fun onStop() {
        super.onStop()
        if (showVideo){
            releasePlayer()
            dialog?.dismiss()
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


    private fun showImageDialog(images:ArrayList<ImageModel>) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_show_post)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.ibChatImageClose.setOnClickListener { dialog.dismiss() }
        dialog.rvImages.adapter = PhotosAdapter(this,images,R.layout.item_recycler_show_images,this)

        dialog.show()
    }
    override fun onAddCommentResponse(response: BaseResponse) {
       // CommonUtil.makeToast(this,response.message?:"")
        etComment.setText("")
        mPresenter.getComments(mSharedPrefManager.authToken!!,postModel!!.data!!.id)
    }

    private var isFirstScreen = true

    override fun onPostUpdate(response: BaseResponse) {
        isFirstScreen = true
        if (deletedItem != null) mAdapter.removeItem(deletedItem!!)
        if (reactedItem != null) {
            when (reactPosition) {
                -1 -> {
                }
                0 -> {
                    reactedItem?.data?.is_like = false
                    reactedItem?.data?.like_type = ""
                    reactedItem?.data?.likes_count =
                        reactedItem?.data?.likes_count?.minus(1) ?: 0
                }
                1 -> {
                    reactedItem?.data?.is_like = true
                    reactedItem?.data?.like_type = "love"
                    reactedItem?.data?.likes_count =
                        reactedItem?.data?.likes_count?.plus(1) ?: 0
                }
                2 -> {
                    reactedItem?.data?.is_like = true
                    reactedItem?.data?.like_type = "laugh"
                    reactedItem?.data?.likes_count =
                        reactedItem?.data?.likes_count?.plus(1) ?: 0
                }
                3 -> {
                    reactedItem?.data?.is_like = true
                    reactedItem?.data?.like_type = "dislike"
                    reactedItem?.data?.likes_count =
                        reactedItem?.data?.likes_count?.plus(1) ?: 0
                }
                else -> {
                }
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onResponseSuccess(data: CommentResponse) {
        swipeRefresh.isRefreshing = false
        rvComments.adapter = CommentsAdapter(this,data.data,R.layout.recycler_item_comment,this)
    }

    override fun onResponseError(errorBody: String) {
        swipeRefresh.isRefreshing = false
        isFirstScreen = true
        SBManager.displayError(
            mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message!!,
        )
    }

    override fun onResponseFailure(t: Throwable) {
        swipeRefresh.isRefreshing = false
        isFirstScreen = true
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

    override fun onPostClicked(position: Int) {


    }

    override fun onFavoriteClick(postId: Int) {
        mPresenter.setPostFavorite(mSharedPrefManager.authToken.toString(), postId)
    }

    override fun onEchoClick(postId: Int, postType: String) {
        showEchoOptions(postId, postType)
    }

    override fun onMovieClick(movieId: Int, movieTitle: String) {

    }

    override fun onMentionsClick(postId: Int) {

    }

    private var reactedItem: HomeModel? = null
    private var reactPosition = -1
    override fun onReactClick(postId: Int, reactionPosition: Int) {
        reactedItem = postModel
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
        ShowProfileActivity.startActivity(mContext as AppCompatActivity,userId)
    }

    override fun onPhotosClick(list: ArrayList<ImageModel>) {
      /*  var list :ArrayList<ImageModel> = ArrayList()
        if (!postModel?.data!!.images.isNullOrEmpty()){
            for (i in postModel?.data!!.images){
                i.type = "image"
                list.add(i)
            }
        }
        if (!postModel?.data!!.videos.isNullOrEmpty()){
            for (i in postModel?.data!!.videos){
                i.type = "video"
                list.add(i)
            }
        }*/
        Log.e("GGGG",list.size.toString())
        showImageDialog(list)
    }

    private var deletedItem: HomeModel? = null
    override fun onDeleteClick(position: Int, postId: Int) {
            mPresenter.deletePost(mSharedPrefManager.authToken.toString(), postId)
            deletedItem = postModel
        finish()
    }

    override fun onFindlayClick(postId: Int) {
            mPresenter.findlayPost(mSharedPrefManager.authToken.toString(), postId)
    }

    override fun onUpdatePrivacyClick(postId: Int) {
        showPostViewers(postId)
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

    private fun showEchoOptions(postId: Int, postType: String) {
        val echoOptionSheet = RoundedBottomSheetDialog(mContext)
        echoOptionSheet.setContentView(
            LayoutInflater.from(mContext)
                .inflate(R.layout.bottom_echo_options, null, false)
        )
        echoOptionSheet.rgEchoOptionViewers.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbEchoOnly -> mPresenter.updatePostPrivacy(
                    mSharedPrefManager.authToken.toString(), postId, "all"
                )
                R.id.rbEchoWithComment -> mPresenter.updatePostPrivacy(
                    mSharedPrefManager.authToken.toString(), postId, "friends"
                )
            }
            echoOptionSheet.dismiss()
        }
        echoOptionSheet.show()
    }

    override fun onCommentClick(position: Int, model: CommentModel) {
        showImageDialog(model.images)
    }

    private fun pickImages() {
        ImagePicker.create(this)
            .toolbarFolderTitle("Folder") // folder selection title
            .toolbarImageTitle("Tap to select") // image selection title
            .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
            .includeVideo(false) // Show video on image picker
            .limit(4) // max images can be selected (99 by default)
            .showCamera(false) // show camera or not (true by default)
            .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
            .enableLog(true) // disabling log
            .start(); // start image picker activity with request code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val images = ImagePicker.getImages(data)
            var imageList:ArrayList<String> = ArrayList()
            for (i in images){
                imageList.add(i.path)
            }
           mPresenter.addComment(mSharedPrefManager.authToken!!,postModel!!.data!!.id,null,imageList)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPhotoClick(position: Int, model: ImageModel) {
        showImageVideoDialog(model.data!!)
    }


}