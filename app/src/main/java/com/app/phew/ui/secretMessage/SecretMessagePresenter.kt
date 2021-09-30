package com.app.phew.ui.secretMessage

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import retrofit2.Response

class SecretMessagePresenter(var view: SecretMessageContract.View?) : SecretMessageContract.Presenter {
    private var mInterActor: SecretMessageContract.InterActor? = SecretMessageInterActor()
    override fun sendSecretMessage(auth: String, userId: Int, message: String?) {
        mInterActor!!.sendSecretMessage(
            auth,userId,message,
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