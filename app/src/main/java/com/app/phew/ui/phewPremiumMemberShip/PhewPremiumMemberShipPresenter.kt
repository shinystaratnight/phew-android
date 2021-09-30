package com.app.phew.ui.phewPremiumMemberShip

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.packages.PackagesResponse
import retrofit2.Response

class PhewPremiumMemberShipPresenter(var view: PhewPremiumMemberShipContract.View?) : PhewPremiumMemberShipContract.Presenter {
    private var mInterActor: PhewPremiumMemberShipContract.InterActor? = PhewPremiumMemberShipInterActor()
    override fun getPackages() {
        mInterActor!!.getPackages(
                object : MVPBaseInteractorOutput<PackagesResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<PackagesResponse>) {
                        view?.apply {
                            hideProgress()
                            onResponseSuccess(response.body()!!)
                        }
                    }

                    override fun onResponseError(response: Response<PackagesResponse>) {
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

    override fun subscribeToPackage(auth: String, packageId: Int) {
        mInterActor!!.subscribeToPackage(auth,packageId,
                object : MVPBaseInteractorOutput<BaseResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<BaseResponse>) {
                        view?.apply {
                            hideProgress()
                            subscribeToSuccess(response.body()!!)
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