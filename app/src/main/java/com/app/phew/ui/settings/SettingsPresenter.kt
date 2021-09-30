package com.app.phew.ui.settings

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import retrofit2.Response

class SettingsPresenter(var view: SettingsContract.View?) : SettingsContract.Presenter {
    private var mInterActor: SettingsContract.InterActor? = SettingsInterActor()
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

    override fun logout(auth: String, device_type: String, device_token: String?) {
        mInterActor!!.logout(
                auth,device_type,device_token,
                object : MVPBaseInteractorOutput<BaseResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<BaseResponse>) {
                        view?.apply {
                            hideProgress()
                            onLogoutSuccess(response.body()!!)
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