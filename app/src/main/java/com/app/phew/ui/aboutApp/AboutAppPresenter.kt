package com.app.phew.ui.aboutApp

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.aboutApp.AboutAppResponse
import com.app.phew.models.chats.ChatsResponse
import retrofit2.Response

class AboutAppPresenter(var view: AboutAppContract.View?) : AboutAppContract.Presenter {
    private var mInterActor: AboutAppContract.InterActor? = AboutAppInterActor()
    override fun getAbout() {
        mInterActor!!.getAbout(
            object : MVPBaseInteractorOutput<AboutAppResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<AboutAppResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<AboutAppResponse>) {
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

    override fun getTerms() {
        mInterActor!!.getTerms(
            object : MVPBaseInteractorOutput<AboutAppResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<AboutAppResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<AboutAppResponse>) {
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