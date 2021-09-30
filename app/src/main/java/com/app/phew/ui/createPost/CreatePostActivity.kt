package com.app.phew.ui.createPost

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.models.BaseResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.esafirm.imagepicker.features.ImagePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.bottom_sheet_post_viewers.*

class CreatePostActivity : ParentActivity(), CreatePostContract.View, PostImagesAdapter.PostImageDeleteListener {
    override val layoutResource: Int
        get() = R.layout.activity_create_post
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
        fun startActivity(appCompatActivity: AppCompatActivity, url:String) {
            appCompatActivity.startActivity(
                    Intent(appCompatActivity, CreatePostActivity::class.java).putExtra("url",url)
            )
        }
    }

    private lateinit var mPresenter: CreatePostPresenter
    private lateinit var mImages: ArrayList<String>
    private lateinit var mImagesAdapter: PostImagesAdapter
    private var mShowPrivacy = "all"
    private var mShowInFindly = 0
    private var mImagesPaths: ArrayList<String>? = null
    private var mVideosPaths: ArrayList<String>? = null
    private var url=""
    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        url = intent?.extras?.getString("url")?:""
        mPresenter = CreatePostPresenter(this)
        mImages = ArrayList()
        mImagesAdapter = PostImagesAdapter(mContext, mImages, this)
        cvCreatePost.setCardBackgroundColor(if (mImages.isNotEmpty())
            ContextCompat.getColor(mContext, R.color.colorPrimary)
        else ContextCompat.getColor(mContext, R.color.colorPrimaryDisabled)
        )
        tvCreatePost.isEnabled = mImages.isNotEmpty()
        rvCreatePostImages.adapter = mImagesAdapter
        mImagesPaths = ArrayList()
        mVideosPaths = ArrayList()

        Glide.with(this).load(mSharedPrefManager.userData.profile_image).into(ivCreatePostProfile)
        tvCreatePostName.text = mSharedPrefManager.userData.fullname

        etCreatePostBody.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                cvCreatePost.setCardBackgroundColor(if (etCreatePostBody.text.toString().isNotEmpty())
                    ContextCompat.getColor(mContext, R.color.colorPrimary)
                else ContextCompat.getColor(mContext, R.color.colorPrimaryDisabled)
                )
                tvCreatePost.isEnabled = etCreatePostBody.text.toString().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        ibCreatePostOptionsImages.setOnClickListener { pickImages() }

        ibCreatePostOptionsViewers.setOnClickListener { showPostViewers() }

        ibCreatePostOptionsFindly.setOnClickListener {
            ImageViewCompat.setImageTintList(ibCreatePostOptionsFindly, ColorStateList.valueOf(
                    if (mShowInFindly == 0)
                        ContextCompat.getColor(mContext, R.color.colorPrimary)
                    else Color.parseColor("#6b6b6b")
            ))
            mShowInFindly = if (mShowInFindly == 0) 1 else 0
        }

        tvCreatePost.setOnClickListener {
            if (url.contains("secret_message")){
                if (etCreatePostBody.text.toString().isNotEmpty()){
                    mPresenter.createPost(url,mSharedPrefManager.authToken.toString(), null, "echo_with_comment" , "normal",
                            text = if (etCreatePostBody.text.toString().isNotEmpty()) etCreatePostBody.text.toString() else null,
                            null, null, null, mShowPrivacy, mShowInFindly, mImagesPaths, mVideosPaths)
                }else{
                    mPresenter.createPost(url,mSharedPrefManager.authToken.toString(), null, "echo_without_comment" , "normal",
                            text = if (etCreatePostBody.text.toString().isNotEmpty()) etCreatePostBody.text.toString() else null,
                            null, null, null, mShowPrivacy, mShowInFindly, mImagesPaths, mVideosPaths)
                }
            }else{
                mPresenter.createPost(url,mSharedPrefManager.authToken.toString(), null, "first", "normal",
                        text = if (etCreatePostBody.text.toString().isNotEmpty()) etCreatePostBody.text.toString() else null,
                        null, null, null, mShowPrivacy, mShowInFindly, mImagesPaths, mVideosPaths)
            }

        }
    }

    private fun pickImages() {
        ImagePicker.create(this)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .includeVideo(true) // Show video on image picker
                .multi()// multi mode (default mode)
                .showCamera(false) // show camera or not (true by default)
                .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(true) // disabling log
                .start() // start image picker activity with request code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            for (image in ImagePicker.getImages(data)) {
                mImages.add(image.path)
                if (getMimeType(image.path).toString().contains("video"))
                    mVideosPaths?.add(image.path)
                else mImagesPaths?.add(image.path)
            }
            mImagesAdapter.notifyDataSetChanged()
            cvCreatePost.setCardBackgroundColor(if (mImages.isNotEmpty())
                ContextCompat.getColor(mContext, R.color.colorPrimary)
            else ContextCompat.getColor(mContext, R.color.colorPrimaryDisabled)
            )
            tvCreatePost.isEnabled = mImages.isNotEmpty()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDeleteImageClick(position: Int) {
        mImagesPaths?.remove(mImagesPaths?.find{it==mImages[position]})
        mVideosPaths?.remove(mVideosPaths?.find{it==mImages[position]})
        mImages.removeAt(position)
        mImagesAdapter.notifyDataSetChanged()
        cvCreatePost.setCardBackgroundColor(if (mImages.isNotEmpty())
            ContextCompat.getColor(mContext, R.color.colorPrimary)
        else ContextCompat.getColor(mContext, R.color.colorPrimaryDisabled)
        )
        tvCreatePost.isEnabled = mImages.isNotEmpty()
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

    override fun onResponseSuccess(data: PostResponse) {
        finish()
    }

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
}