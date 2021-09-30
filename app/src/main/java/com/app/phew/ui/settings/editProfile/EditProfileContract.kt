package com.app.phew.ui.settings.editProfile

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.cities.CitiesResponse
import com.app.phew.models.countries.CountriesResponse
import com.app.phew.models.images.ImageModel

class EditProfileContract {
    interface Presenter : MVPBasePresenter {
        fun updateProfile(
                auth: String,
                fullname: String, mobile: String, email: String,
                images: ArrayList<ImageModel>?)

        fun deletePicture(auth: String,id:Int)
    }

    interface View : MVPBaseApiView<LoginResponse> {
        fun showFieldError(field: String)
        fun deletePictureSuccess(response: BaseResponse)
    }

    interface InterActor {
        fun updateProfile(
                auth: String,
                fullname: String, mobile: String, email: String,
                images: ArrayList<ImageModel>?, output: MVPBaseInteractorOutput<LoginResponse>
        )

        fun deletePicture(auth: String,id:Int,output: MVPBaseInteractorOutput<BaseResponse>)
    }
}