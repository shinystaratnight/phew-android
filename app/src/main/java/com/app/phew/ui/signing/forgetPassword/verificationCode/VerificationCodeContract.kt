package com.app.phew.ui.signing.forgetPassword.verificationCode

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse

class VerificationCodeContract {
    interface Presenter : MVPBasePresenter {
        fun verifyAccount(mobile: String,code:String,verifyType:String)
        fun resendCode(mobile: String)
    }

    interface View : MVPBaseApiView<BaseResponse> {
        fun showFieldError(field: String)
    }

    interface InterActor {
        fun verifyAccount(mobile: String,code:String,verifyType:String, output: MVPBaseInteractorOutput<BaseResponse>)
        fun resendCode(mobile: String, output: MVPBaseInteractorOutput<BaseResponse>)
    }
}