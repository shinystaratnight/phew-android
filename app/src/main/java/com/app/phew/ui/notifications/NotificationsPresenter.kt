package com.app.phew.ui.notifications

import com.app.phew.base.BaseFragment
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.notifications.NotificationsResponse
import retrofit2.Response

class NotificationsPresenter(var view: NotificationsContract.View?) : NotificationsContract.Presenter {
    private var mInterActor: NotificationsContract.InterActor? = NotificationsInterActor()
    override fun getNotifications(auth: String) {
        mInterActor!!.getNotifications(
                auth,
                object : MVPBaseInteractorOutput<NotificationsResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<NotificationsResponse>) {
                        view?.apply {
                            hideProgress()
                            onResponseSuccess(response.body()!!)
                        }
                    }

                    override fun onResponseError(response: Response<NotificationsResponse>) {
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

    override fun deleteNotifications(auth: String, notificationId: String) {
        mInterActor!!.deleteNotifications(
                auth,notificationId,
                object : MVPBaseInteractorOutput<BaseResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<BaseResponse>) {
                        view?.apply {
                            hideProgress()
                            onDeleteSuccess(response.body()!!)
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