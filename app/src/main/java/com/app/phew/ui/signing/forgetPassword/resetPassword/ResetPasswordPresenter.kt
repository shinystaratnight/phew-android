package com.app.phew.ui.signing.forgetPassword.resetPassword

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import retrofit2.Response

class ResetPasswordPresenter(var view: ResetPasswordContract.View?) : ResetPasswordContract.Presenter {
    private var mInterActor: ResetPasswordContract.InterActor? = ResetPasswordInterActor()
    override fun resetPassword(mobile: String, code: String, password: String, confirmPassword: String) {
        when {
            password.isEmpty() -> view?.showFieldError("password")
            password!=confirmPassword -> view?.showFieldError("notMatch")
            else -> mInterActor!!.resetPassword(mobile,code,password,confirmPassword,
                    object : MVPBaseInteractorOutput<BaseResponse> {
                        override fun onServiceRunning() {
                            view?.showProgress()
                        }

                        override fun onResponseSuccess(response: Response<BaseResponse>) {
                            view?.apply {
                                hideProgress()
                                onResponseSuccess(response.body()!!)
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


    override fun onDestroy() {
        view = null
        mInterActor = null
    }
}