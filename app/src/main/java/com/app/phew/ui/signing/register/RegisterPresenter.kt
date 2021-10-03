package com.app.phew.ui.signing.register

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.cities.CitiesResponse
import com.app.phew.models.countries.CountriesResponse
import com.app.phew.utils.ValidationUtils
import retrofit2.Response

class RegisterPresenter(var view: RegisterContract.View?) : RegisterContract.Presenter {
    private var mInterActor: RegisterContract.InterActor? = RegisterInterActor()

    override fun register(
        fullname: String, mobile: String, email: String, password: String, confirmPassword: String,
        images: ArrayList<String>?,
        countryId: Int?, cityId: Int?, deviceType: String?, deviceToken: String?
    ) {
        when {
            fullname.isEmpty() -> view?.showFieldError("fullname")
            email.isEmpty() -> view?.showFieldError("email")
            !ValidationUtils.isEmail(email)-> view?.showFieldError("notEmail")
            mobile.isEmpty() -> view?.showFieldError("mobile")
//            cityId!! < 0 ->{view?.showFieldError("country")}
//            countryId!! < 0 ->{view?.showFieldError("cityId")}
            password.isEmpty() -> view?.showFieldError("password")
            password!=confirmPassword -> view?.showFieldError("notMatch")
            else -> mInterActor!!.register(
                fullname, mobile, email, password,confirmPassword, images, null,
                null, deviceType, deviceToken, object : MVPBaseInteractorOutput<BaseResponse> {
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

    override fun getCountries() {
        mInterActor!!.getCountries(
        object : MVPBaseInteractorOutput<CountriesResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<CountriesResponse>) {
                    view?.apply {
                        hideProgress()
                        onGetCountriesSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<CountriesResponse>) {
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

    override fun getCities(countryId: Int) {
        mInterActor!!.getCities(countryId,
            object : MVPBaseInteractorOutput<CitiesResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<CitiesResponse>) {
                    view?.apply {
                        hideProgress()
                        onGetCitiesSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<CitiesResponse>) {
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