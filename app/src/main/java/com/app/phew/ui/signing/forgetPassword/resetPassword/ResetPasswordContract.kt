package com.app.phew.ui.signing.forgetPassword.resetPassword

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
class ResetPasswordContract {
    interface Presenter : MVPBasePresenter {
        fun resetPassword(mobile: String,code:String,password:String,confirmPassword:String)
    }

    interface View : MVPBaseApiView<BaseResponse> {
        fun showFieldError(field: String)
    }

    interface InterActor {
        fun resetPassword(mobile: String,code:String,password:String,confirmPassword:String, output: MVPBaseInteractorOutput<BaseResponse>
        )
    }
}