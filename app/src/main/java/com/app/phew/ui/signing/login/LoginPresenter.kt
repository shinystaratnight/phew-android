package com.app.phew.ui.signing.login

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.auth.LoginResponse
import retrofit2.Response

class LoginPresenter(var view: LoginContract.View?) : LoginContract.Presenter {
    private var mInterActor: LoginContract.InterActor? = LoginInterActor()

    override fun login(
            identifier: String, password: String, deviceType: String?, deviceToken: String?
    ) {
        when {
            identifier.isEmpty() -> view?.showFieldError("identifier")
            password.isEmpty() -> view?.showFieldError("password")
            else -> mInterActor!!.login(
                    identifier, password, deviceType, deviceToken,
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
    }

    override fun socialLogin(fullName: String, providerType: String, providerId: String, deviceType: String?, deviceToken: String?) {
        mInterActor!!.socialLogin(
                fullName, providerType, providerId, deviceType, deviceToken,
                object : MVPBaseInteractorOutput<LoginResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<LoginResponse>) {
                        view?.apply {
                            hideProgress()
                            onSocialLogin(response.body()!!)
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