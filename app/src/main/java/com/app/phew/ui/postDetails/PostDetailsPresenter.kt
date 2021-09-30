package com.app.phew.ui.postDetails

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.comment.CommentResponse
import retrofit2.Response

class PostDetailsPresenter(var view: PostDetailsContract.View?) : PostDetailsContract.Presenter {
    private var mInterActor: PostDetailsContract.InterActor? = PostDetailsInterActor()
    override fun getComments(auth: String, postId: Int) {
        mInterActor!!.getComments(auth,postId ,
            object : MVPBaseInteractorOutput<CommentResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<CommentResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<CommentResponse>) {
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

    override fun addComment(auth: String, postId: Int, text: String?, images: ArrayList<String>?) {
        mInterActor!!.addComment(auth,postId,text,images ,
            object : MVPBaseInteractorOutput<BaseResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<BaseResponse>) {
                    view?.apply {
                        hideProgress()
                        onAddCommentResponse(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<BaseResponse>) {
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

    override fun setPostFavorite(auth: String, postId: Int) {
        mInterActor!!.setPostFavorite(auth, postId, object : MVPBaseInteractorOutput<BaseResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<BaseResponse>) {
                view?.apply {
                    hideProgress()
                    onPostUpdate(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<BaseResponse>) {
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

    override fun reactPost(auth: String, postId: Int, type: String) {
        mInterActor!!.reactPost(auth, postId, type, object : MVPBaseInteractorOutput<BaseResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<BaseResponse>) {
                view?.apply {
                    hideProgress()
                    onPostUpdate(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<BaseResponse>) {
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

    override fun deletePost(auth: String, postId: Int) {
        mInterActor!!.deletePost(auth, postId, object : MVPBaseInteractorOutput<BaseResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<BaseResponse>) {
                view?.apply {
                    hideProgress()
                    onPostUpdate(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<BaseResponse>) {
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

    override fun findlayPost(auth: String, postId: Int) {
        mInterActor!!.findlayPost(auth, postId, object : MVPBaseInteractorOutput<BaseResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<BaseResponse>) {
                view?.apply {
                    hideProgress()
                    onPostUpdate(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<BaseResponse>) {
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

    override fun updatePostPrivacy(auth: String, postId: Int, showPrivacy: String) {
        mInterActor!!.updatePostPrivacy(
            auth,
            postId,
            showPrivacy,
            object : MVPBaseInteractorOutput<BaseResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<BaseResponse>) {
                    view?.apply {
                        hideProgress()
                        onPostUpdate(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<BaseResponse>) {
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