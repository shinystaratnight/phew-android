package com.app.phew.ui.createPost

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.home.PostResponse

class CreatePostContract {
    interface Presenter : MVPBasePresenter {
        fun createPost(
                url: String, auth: String, postId: Int?, postType: String, activityType: String, text: String?,
                location: String?, watching: String?, friendsWith: String?, showPrivacy: String?,
                showInFindly: Int?, images: ArrayList<String>?, videos: ArrayList<String>?
        )
    }

    interface View : MVPBaseApiView<PostResponse>

    interface InterActor {
        fun createPost(
                url: String, auth: String, postId: Int?, postType: String, activityType: String, text: String?,
                location: String?, watching: String?, friendsWith: String?, showPrivacy: String?,
                showInFindly: Int?, images: ArrayList<String>?, videos: ArrayList<String>?,
                output: MVPBaseInteractorOutput<PostResponse>
        )
    }
}