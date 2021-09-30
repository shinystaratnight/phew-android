package com.app.phew.ui.settings.notificationSettings

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.models.secretMessages.SecretMessageResponse
import retrofit2.Response

class NotificationsSettingsPresenter(var view: NotificationsSettingsContract.View?) : NotificationsSettingsContract.Presenter {
    private var mInterActor: NotificationsSettingsContract.InterActor? = NotificationsSettingsInterActor()
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

    override fun updateSettings(
        auth: String,
        method: String,
        all_notices: Int?,
        notification_to_new_followers: Int?,
        notification_to_mention: Int?
    ) {
        mInterActor!!.updateSettings(
            auth,method,all_notices,notification_to_new_followers,notification_to_mention,
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

    override fun updatePackageSettings(auth: String, method: String, inactive: Int?) {
        mInterActor!!.updatePackageSettings(
            auth,method,inactive,
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


    override fun onDestroy() {
        view = null
        mInterActor = null
    }
}