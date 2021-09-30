package com.app.phew.ui.signing.forgetPassword.enterMobile

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import retrofit2.Response

class EnterMobilePresenter(var view: EnterMobileContract.View?) : EnterMobileContract.Presenter {
    private var mInterActor: EnterMobileContract.InterActor? = EnterMobileInterActor()
    override fun forgetPassword(mobile: String) {
        when {
            mobile.isEmpty() -> view?.showFieldError("mobile")
            mobile.length<6 -> view?.showFieldError("shortMobile")
            else -> mInterActor!!.forgetPassword(mobile,
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