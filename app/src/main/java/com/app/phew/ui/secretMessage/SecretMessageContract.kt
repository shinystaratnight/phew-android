package com.app.phew.ui.secretMessage

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse

class SecretMessageContract {
    interface Presenter : MVPBasePresenter {
        fun sendSecretMessage(
            auth: String,
            userId: Int,
            message: String?
        )
    }

    interface View : MVPBaseApiView<BaseResponse>

    interface InterActor {
        fun sendSecretMessage(
            auth: String,
            userId: Int,
            message: String?,
            output: MVPBaseInteractorOutput<BaseResponse>
        )

    }
}