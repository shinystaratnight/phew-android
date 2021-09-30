package com.app.phew.ui.signing.login

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.auth.LoginResponse

class LoginContract {
    interface Presenter : MVPBasePresenter {
        fun login(identifier: String, password: String, deviceType: String?, deviceToken: String?)
        fun socialLogin(
                fullName: String, providerType: String, providerId: String,
                deviceType: String?, deviceToken: String?
        )
    }

    interface View : MVPBaseApiView<LoginResponse> {
        fun showFieldError(field: String)
        fun onSocialLogin(data: LoginResponse)
    }

    interface InterActor {
        fun login(
                identifier: String, password: String, deviceType: String?, deviceToken: String?,
                output: MVPBaseInteractorOutput<LoginResponse>
        )

        fun socialLogin(
                fullName: String, providerType: String, providerId: String, deviceType: String?,
                deviceToken: String?, output: MVPBaseInteractorOutput<LoginResponse>
        )
    }
}