package com.app.phew.ui.editPassword


import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import retrofit2.Response

class EditPasswordPresenter(var view: EditPasswordContract.View?) : EditPasswordContract.Presenter {

    private var mInterActor: EditPasswordContract.InterActor? = EditPasswordInterActor()
    override fun editPassword(auth: String, oldPassword: String, newPassword: String) {
        when {
            oldPassword.isEmpty()  -> view?.showFieldError("oldPassword")
            newPassword.length<6 -> view?.showFieldError("shortPassword")
            else -> mInterActor!!.editPassword(auth,oldPassword,newPassword,
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