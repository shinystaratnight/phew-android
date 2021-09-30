package com.app.phew.ui.dashboard

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.models.findly.contries.FindlyCountriesResponse
import com.app.phew.models.findly.findlyCities.FindlyCitiesResponse
import retrofit2.Response

class FindlyPresenter(var view: FindlyContract.View?) : FindlyContract.Presenter {
    private var mInterActor: FindlyContract.InterActor? = FindlyInterActor()
    override fun getFindlyCountries() {
        mInterActor!!.getFindlyCountries(
            object : MVPBaseInteractorOutput<FindlyCountriesResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<FindlyCountriesResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<FindlyCountriesResponse>) {
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

    override fun getFindlyCities(countriesId: Int) {
        mInterActor!!.getFindlyCities(countriesId,
            object : MVPBaseInteractorOutput<FindlyCitiesResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<FindlyCitiesResponse>) {
                    view?.apply {
                        hideProgress()
                        getFindlyCitiesSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<FindlyCitiesResponse>) {
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