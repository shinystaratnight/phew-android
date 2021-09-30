package com.app.phew.ui.editPassword

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse


class EditPasswordContract {
    interface Presenter : MVPBasePresenter {

        fun editPassword(
            auth: String,
            oldPassword: String,
            newPassword: String
        )


    }

    interface View : MVPBaseApiView<BaseResponse> {
        fun showFieldError(field: String)
    }

    interface InterActor {
        fun editPassword(
            auth: String,
            oldPassword: String,
            newPassword: String,
            output: MVPBaseInteractorOutput<BaseResponse>
        )


    }
}