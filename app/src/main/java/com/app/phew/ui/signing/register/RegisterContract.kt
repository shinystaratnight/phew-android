package com.app.phew.ui.signing.register

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.cities.CitiesResponse
import com.app.phew.models.countries.CountriesResponse

class RegisterContract {
    interface Presenter : MVPBasePresenter {
        fun register(
            fullname: String, mobile: String, email: String,
            password: String, confirmPassword: String, images: ArrayList<String>?, countryId: Int?,
            cityId: Int?, deviceType: String?, deviceToken: String?
        )
        fun getCountries()
        fun getCities(countryId: Int)
        fun socialLogin(
            fullName: String, providerType: String, providerId: String,
            deviceType: String?, deviceToken: String?
        )
    }

    interface View : MVPBaseApiView<BaseResponse> {
        fun showFieldError(field: String)
        fun onGetCountriesSuccess(response: CountriesResponse)
        fun onGetCitiesSuccess(response: CitiesResponse)
        fun onSocialLogin(data: LoginResponse)
    }

    interface InterActor {
        fun register(
            fullname: String, mobile: String, email: String, password: String, confirmPassword: String,
            images: ArrayList<String>?, countryId: Int?, cityId: Int?, deviceType: String?,
            deviceToken: String?, output: MVPBaseInteractorOutput<BaseResponse>
        )
        fun getCountries(output: MVPBaseInteractorOutput<CountriesResponse>)
        fun getCities(countryId: Int,output: MVPBaseInteractorOutput<CitiesResponse>)
        fun socialLogin(
            fullName: String, providerType: String, providerId: String, deviceType: String?,
            deviceToken: String?, output: MVPBaseInteractorOutput<LoginResponse>
        )
    }
}