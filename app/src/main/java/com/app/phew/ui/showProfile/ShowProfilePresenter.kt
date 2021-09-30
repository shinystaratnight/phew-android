package com.app.phew.ui.showProfile

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import retrofit2.Response

class ShowProfilePresenter(var view: ShowProfileContract.View?) : ShowProfileContract.Presenter {
    private var mInterActor: ShowProfileContract.InterActor? = ShowProfileInterActor()
    override fun getProfile(auth: String, userId: Int) {
        mInterActor!!.getProfile(
            auth,userId,
            object : MVPBaseInteractorOutput<LoginResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<LoginResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<LoginResponse>) {
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

    override fun follow(auth: String, userId: Int) {
        mInterActor!!.follow(
            auth,userId,
            object : MVPBaseInteractorOutput<BaseResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<BaseResponse>) {
                    view?.apply {
                        hideProgress()
                        onFollowSuccess(response.body()!!)
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

    override fun sendRequest(auth: String, userId: Int) {
        mInterActor!!.sendRequest(
            auth,userId,
            object : MVPBaseInteractorOutput<BaseResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<BaseResponse>) {
                    view?.apply {
                        hideProgress()
                        onFollowSuccess(response.body()!!)
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

    override fun cancelRequest(auth: String, userId: Int) {
        mInterActor!!.cancelRequest(
            auth,userId,
            object : MVPBaseInteractorOutput<BaseResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<BaseResponse>) {
                    view?.apply {
                        hideProgress()
                        onFollowSuccess(response.body()!!)
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

    override fun removeFriend(auth: String, userId: Int) {
        mInterActor!!.removeFriend(auth,userId, object : MVPBaseInteractorOutput<BaseResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<BaseResponse>) {
                view?.apply {
                    hideProgress()
                    onFollowSuccess(response.body()!!)
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

    override fun acceptFriendRequest(auth: String, userId: Int) {
        mInterActor!!.acceptFriendRequest(
            auth,userId,
            object : MVPBaseInteractorOutput<BaseResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<BaseResponse>) {
                    view?.apply {
                        hideProgress()
                        onAcceptSuccess(response.body()!!)
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

    override fun rejectFriendRequest(auth: String, userId: Int) {
        mInterActor!!.rejectFriendRequest(
            auth,userId,
            object : MVPBaseInteractorOutput<BaseResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<BaseResponse>) {
                    view?.apply {
                        hideProgress()
                        onAcceptSuccess(response.body()!!)
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