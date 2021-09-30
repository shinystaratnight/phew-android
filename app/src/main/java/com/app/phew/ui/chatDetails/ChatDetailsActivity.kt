package com.app.phew.ui.chatDetails

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.chatDetails.ChatDetailsModel
import com.app.phew.models.chatDetails.ChatDetailsResponse
import com.app.phew.models.chatDetails.SendMessageResponse
import com.app.phew.models.images.ImageModel
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener
import com.asynctaskcoffee.audiorecorder.worker.Recorder
import com.bumptech.glide.Glide
import com.devlomi.record_view.OnRecordListener
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
import com.rygelouv.audiosensei.player.AudioSenseiListObserver
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.activity_chat_details.rel
import kotlinx.android.synthetic.main.activity_chat_details.swipeRefresh
import kotlinx.android.synthetic.main.activity_secret_messages.*
import kotlinx.android.synthetic.main.dialog_show_message.*
import kotlinx.android.synthetic.main.dialog_show_post.*
import kotlinx.android.synthetic.main.dialog_show_post.ibChatImageClose
import java.io.File

class ChatDetailsActivity : ParentActivity() , ChatDetailsContract.View , AudioRecordListener,
    Player.EventListener, ChatDetailsAdapter.OnMessageClick {
    override val layoutResource: Int
        get() = R.layout.activity_chat_details
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
        fun startActivity(appCompatActivity: AppCompatActivity, userId: Int) {
            appCompatActivity.startActivity(Intent(appCompatActivity, ChatDetailsActivity::class.java).putExtra("userId", userId))
        }
    }



    private lateinit var mPresenter: ChatDetailsPresenter
    lateinit var recorder: Recorder
    private var messages: ArrayList<ChatDetailsModel> = ArrayList()
    private var chatAdapter = ChatDetailsAdapter(this, messages,this)
    private var userId = 0
    private var load = true
    private var sendVoice = false
    var filetype =""
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        mPresenter = ChatDetailsPresenter(this)
        userId = intent?.extras?.getInt("userId")?:0
        mPresenter.getChatDetails(
                mSharedPrefManager.authToken!!, userId

        )



        recorder = Recorder(this)
        record_button.setRecordView(record_view)
        swipeRefresh.setColorSchemeColors(Color.parseColor("#E21E2C"))
        swipeRefresh.setOnRefreshListener {
            mPresenter.getChatDetails(
                    mSharedPrefManager.authToken!!, userId

            )
        }
        imvSend.setOnClickListener {
            if (etMessage.text.toString().isNotEmpty())
                mPresenter.sendMessage(
                        mSharedPrefManager.authToken!!, userId, "text", etMessage.text.toString())
        }
        imvSendMedia.setOnClickListener {
            pickImages()
        }

        record_view.setOnRecordListener(object : OnRecordListener {
            override fun onStart() {
                //Start Recording..
                Log.e("RecordView", "onStart")
                etMessage.visibility = View.INVISIBLE
                record_view.visibility = View.VISIBLE
                recorder.startRecord()
            }

            override fun onCancel() {

                sendVoice = false
                recorder.stopRecording()
                val timer = object : CountDownTimer(2000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {

                        etMessage.visibility = View.VISIBLE
                        record_view.visibility = View.GONE
                    }
                }
                timer.start()
                Log.e("RecordView", "onCancel")

            }

            override fun onFinish(recordTime: Long) {
                //Stop Recording..
                sendVoice = true
                etMessage.visibility = View.VISIBLE
                record_view.visibility = View.GONE
                Log.e("RecordView", "onFinish")
                recorder.stopRecording()
            }

            override fun onLessThanSecond() {
                sendVoice = false
                etMessage.visibility = View.VISIBLE
                record_view.visibility = View.GONE
                //When the record time is less than One Second
                Log.e("RecordView", "onLessThanSecond")
            }
        })

        record_button.setOnRecordClickListener {
            Toast.makeText(this@ChatDetailsActivity, "RECORD BUTTON CLICKED", Toast.LENGTH_SHORT)
                .show()
            Log.d("RecordButton", "RECORD BUTTON CLICKED")
        }
    }

    override fun onSendMessageSuccess(response: SendMessageResponse) {
        load = false
        etMessage.setText("")
        mPresenter.getChatDetails(
                mSharedPrefManager.authToken!!, userId
        )
    }

    override fun onResponseSuccess(data: ChatDetailsResponse) {
        swipeRefresh.isRefreshing = false
        messages = data.data
        if (messages.isNullOrEmpty()) {
            rvChatMessage.visibility = View.INVISIBLE
        } else {
            messages.reverse()
            rvChatMessage.visibility = View.VISIBLE
            val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mContext)
            layoutManager.stackFromEnd = true
            chatAdapter = ChatDetailsAdapter(this, messages,this)
            rvChatMessage.layoutManager = layoutManager
            rvChatMessage.setItemViewCacheSize(data.data.size)
            rvChatMessage.adapter = chatAdapter
        }
        AudioSenseiListObserver.getInstance().registerLifecycle(lifecycle);


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
        if (load){
            rel.visibility = View.VISIBLE
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }


    }


    override fun hideProgress() {
        swipeRefresh.isRefreshing = false
        if (load) {
            rel.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onAudioReady(audioUri: String?) {
        Log.e("Recorde", audioUri + "f")
       if (sendVoice){
           mPresenter.sendMessage(
                   mSharedPrefManager.authToken!!, userId, "voice_message", audioUri!!
           )
       }else{
           val file = File(audioUri!!)
           if (file.exists()) {
               file.delete()
           }
       }
    }

    override fun onRecordFailed(errorMessage: String?) {
       CommonUtil.makeToast(this, R.string.some_error_happened)
    }


    private fun pickImages() {
        ImagePicker.create(this)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .includeVideo(true) // Show video on image picker
                .single()// multi mode (default mode)
                .limit(1) // max images can be selected (99 by default)
                .showCamera(false) // show camera or not (true by default)
                .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(true) // disabling log
                .start(); // start image picker activity with request code
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data).path

            mPresenter.sendMessage(mSharedPrefManager.authToken!!,
                    userId,
                    if (getMimeType(image)!!.contains("image"))"image" else "video"
                    ,image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url.substring(url.lastIndexOf('.'),url.length))
        Log.e("dddd",extension)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }


          private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(this, "exoplayer-sample")
    }
    private lateinit var simpleExoplayer: SimpleExoPlayer
    val dialog : Dialog?=null
    var showVideo = false
    private var playbackPosition: Long = 0
    private fun showImageDialog(file: String,fileType:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_show_message)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        if (fileType=="image"){
            Glide.with(this).load(file).into(dialog.ivPostImage)
            dialog.ivPostImage.visibility = View.VISIBLE
            dialog.video_view.visibility = View.GONE
            showVideo = false
            dialog.ibChatImageClose.setOnClickListener { dialog.dismiss()
                 }
            Log.e("FFFFF",fileType)
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setCancelable(false)
            dialog.show()
        }else{
            Log.e("FFFFF",fileType)
            dialog.ivPostImage.visibility = View.GONE
            dialog.video_view.visibility = View.VISIBLE
            showVideo = true
            initializePlayer(file)
            dialog.ibChatImageClose.setOnClickListener { dialog.dismiss()
                releasePlayer() }
        }



    }
    private fun initializePlayer(videoUrl: String) {
        if (showVideo){
            simpleExoplayer = SimpleExoPlayer.Builder(this).build()
            preparePlayer(videoUrl)
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_show_message)
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.ivPostImage.visibility = View.GONE
            dialog.video_view.visibility = View.VISIBLE
            showVideo = true
            dialog.ibChatImageClose.setOnClickListener { dialog.dismiss()
                releasePlayer() }
            dialog.video_view?.player = simpleExoplayer
            dialog.video_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            simpleExoplayer.seekTo(playbackPosition)
            simpleExoplayer.playWhenReady = true
            simpleExoplayer.addListener(this)
            simpleExoplayer.videoScalingMode = Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setCancelable(false)
            dialog.show()
        }else{
            Log.e("ffff","ffff")
        }

    }


    override fun onRestart() {
        super.onRestart()

    }

    override fun onStop() {
        super.onStop()
        if (filetype == "video"&&showVideo){
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

    override fun onMessage(model: ChatDetailsModel) {
       showImageDialog(model.message,model.message_type)
    }

}