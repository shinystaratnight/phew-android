package com.app.phew.ui.createPost

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.home.PostResponse
import retrofit2.Response

class CreatePostPresenter(var view: CreatePostContract.View?) : CreatePostContract.Presenter {
    private var mInterActor: CreatePostContract.InterActor? = CreatePostInterActor()

    override fun createPost(
            url: String,auth: String, postId: Int?, postType: String, activityType: String, text: String?,
            location: String?, watching: String?, friendsWith: String?, showPrivacy: String?,
            showInFindly: Int?, images: ArrayList<String>?, videos: ArrayList<String>?) {
        mInterActor!!.createPost(url,auth, postId, postType, activityType, text, location, watching,
                friendsWith, showPrivacy, showInFindly, images, videos, object : MVPBaseInteractorOutput<PostResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<PostResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseSuccess(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<PostResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseError(response.errorBody()!!.string())
                }
            }

            override fun onResponseFailure(t: Throwable) {
                view?.apply {
                    hideProgress()
                    onResponseFailure(t)
                }
            }
        })
    }

    override fun onDestroy() {
        view = null
        mInterActor = null
    }
}