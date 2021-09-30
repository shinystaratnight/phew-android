package com.app.phew.ui.settings.editProfile

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.images.ImageModel
import com.app.phew.utils.ValidationUtils
import retrofit2.Response

class EditProfilePresenter(var view: EditProfileContract.View?) : EditProfileContract.Presenter {
    private var mInterActor: EditProfileContract.InterActor? = EditProfileInterActor()
    override fun updateProfile(auth: String, fullname: String, mobile: String, email: String, images: ArrayList<ImageModel>?) {
        when {
            fullname.isEmpty() -> view?.showFieldError("fullname")
            email.isEmpty() -> view?.showFieldError("email")
            !ValidationUtils.isEmail(email)-> view?.showFieldError("notEmail")
            mobile.isEmpty() -> view?.showFieldError("mobile")
            else -> mInterActor!!.updateProfile(
                   auth, fullname, mobile, email,  images, object : MVPBaseInteractorOutput<LoginResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<LoginResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
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
    }

    override fun deletePicture(auth: String, id: Int) {
        mInterActor!!.deletePicture(
                auth, id, object : MVPBaseInteractorOutput<BaseResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<BaseResponse>) {
                view?.apply {
                    hideProgress()
                    deletePictureSuccess(response.body()!!)
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