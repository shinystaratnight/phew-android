package com.app.phew.ui.friendRequest

import com.app.phew.base.BaseFragment
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.friendRquestes.FriendRequestesResponse
import com.app.phew.models.notifications.NotificationsResponse
import retrofit2.Response

class FriendRequestPresenter(var view: FriendRequestContract.View?) : FriendRequestContract.Presenter {
    private var mInterActor: FriendRequestContract.InterActor? = FriendRequestInterActor()
    override fun getRequests(auth: String, userId: Int, filter: String) {
        mInterActor!!.getRequests(
            auth,userId,filter,
            object : MVPBaseInteractorOutput<FriendRequestesResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<FriendRequestesResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<FriendRequestesResponse>) {
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