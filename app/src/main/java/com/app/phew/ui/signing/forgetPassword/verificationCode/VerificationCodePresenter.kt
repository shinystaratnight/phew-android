package com.app.phew.ui.signing.forgetPassword.verificationCode

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import retrofit2.Response

class VerificationCodePresenter(var view: VerificationCodeContract.View?) : VerificationCodeContract.Presenter {
    private var mInterActor: VerificationCodeContract.InterActor? = VerificationCodeInterActor()
    override fun verifyAccount(mobile: String, code: String,verifyType:String) {
        when {
            code.length<4 -> view?.showFieldError("code")
            else -> mInterActor!!.verifyAccount(mobile,code,verifyType,
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

    override fun resendCode(mobile: String) {
        mInterActor!!.resendCode(mobile,
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


    override fun onDestroy() {
        view = null
        mInterActor = null
    }
}