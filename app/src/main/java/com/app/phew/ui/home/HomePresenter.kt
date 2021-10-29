package com.app.phew.ui.home

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.home.HomeResponse
import com.app.phew.models.home.ScreenShotBody
import com.app.phew.models.movies.MoviesSearchResponse
import retrofit2.Response

class HomePresenter(var view: HomeContract.View?) : HomeContract.Presenter {
    private var mInterActor: HomeContract.InterActor? = HomeInterActor()

    override fun getHome(url: String, auth: String, type: String, page: Int) {
        mInterActor!!.getHome(
            url, auth, type, page, object : MVPBaseInteractorOutput<HomeResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<HomeResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<HomeResponse>) {
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

    override fun screenShot(auth: String, screenShotBody: ScreenShotBody) {
        mInterActor!!.screenShot(
            auth,
            screenShotBody,
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

    override fun updatePostEcho(auth: String, postId: Int, showPrivacy: String, commentText: String?) {
        if (commentText != null) {
            mInterActor!!.echoWithComment(
                auth,
                postId,
                commentText,
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
        } else {
            mInterActor!!.echoWithoutComment(
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
    }

    override fun searchMovie(movieId: Int, query: String) {
        mInterActor!!.searchMovie(query, object : MVPBaseInteractorOutput<MoviesSearchResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<MoviesSearchResponse>) {
                view?.apply {
                    hideProgress()
                    onSearchMovie(response.body()!!, movieId)
                }
            }

            override fun onResponseError(response: Response<MoviesSearchResponse>) {
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