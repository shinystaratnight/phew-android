package com.app.phew.ui.settings

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse

class SettingsContract {
    interface Presenter : MVPBasePresenter {
        fun getProfile(auth: String, userId: Int)

        fun logout(
            auth: String,
            device_type: String,
            device_token: String?
        )
    }

    interface View : MVPBaseApiView<LoginResponse> {
        fun  onLogoutSuccess(response: BaseResponse)
    }

    interface InterActor {
        fun getProfile(
            auth: String, userId: Int,
            output: MVPBaseInteractorOutput<LoginResponse>
        )

        fun logout(
                auth: String,
                device_type: String,
                device_token: String?,
            output: MVPBaseInteractorOutput<BaseResponse>
        )

    }
}