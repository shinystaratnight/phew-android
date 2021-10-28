package com.app.phew.ui.createEchoPost

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import retrofit2.Response

class CreateEchoPostPresenter(var view: CreateEchoPostContract.View?) : CreateEchoPostContract.Presenter {
    private var mInterActor: CreateEchoPostContract.InterActor? = CreateEchoPostInterActor()

    override fun createEchoPost(auth: String, postId: Int, text: String,) {
        mInterActor!!.createEchoPost(auth, postId, text, object : MVPBaseInteractorOutput<BaseResponse> {
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