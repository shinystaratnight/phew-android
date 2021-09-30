package com.app.phew.ui.signing.forgetPassword.enterMobile

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse

class EnterMobileContract {
    interface Presenter : MVPBasePresenter {
        fun forgetPassword(mobile: String)
    }

    interface View : MVPBaseApiView<BaseResponse> {
        fun showFieldError(field: String)
    }

    interface InterActor {
        fun forgetPassword(mobile: String, output: MVPBaseInteractorOutput<BaseResponse>
        )
    }
}