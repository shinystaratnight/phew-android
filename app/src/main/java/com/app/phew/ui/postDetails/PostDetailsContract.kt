package com.app.phew.ui.postDetails

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.comment.CommentResponse

class PostDetailsContract {
    interface Presenter : MVPBasePresenter {

        fun getComments(auth: String, postId: Int)
        fun addComment(
            auth: String, postId: Int, text: String?,
            images: ArrayList<String>?
        )
        fun deletePost(auth: String, postId: Int)
        fun findlayPost(auth: String, postId: Int)
        fun updatePostPrivacy(auth: String, postId: Int, showPrivacy: String)
        fun setPostFavorite(auth: String, postId: Int)
        fun reactPost(auth: String, postId: Int, type: String)

    }

    interface View : MVPBaseApiView<CommentResponse> {
        fun  onAddCommentResponse(response: BaseResponse)
        fun onPostUpdate(response: BaseResponse)
    }

    interface InterActor {
        fun getComments(auth: String, postId: Int,
                        output: MVPBaseInteractorOutput<CommentResponse>)
        fun addComment(
            auth: String,
            postId: Int,
            text: String?,
            images: ArrayList<String>?,
            output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun deletePost(auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>)

        fun findlayPost(auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>)
        fun updatePostPrivacy(
            auth: String, postId: Int, showPrivacy: String,
            output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun setPostFavorite(
            auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun reactPost(
            auth: String, postId: Int, type: String, output: MVPBaseInteractorOutput<BaseResponse>
        )


    }
}