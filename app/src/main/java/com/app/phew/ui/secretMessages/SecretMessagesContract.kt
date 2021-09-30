package com.app.phew.ui.secretMessages

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.secretMessages.SecretMessageResponse

class SecretMessagesContract {
    interface Presenter : MVPBasePresenter {
        fun getSecretMessages(auth: String)
    }

    interface View : MVPBaseApiView<SecretMessageResponse> {}

    interface InterActor {
        fun getSecretMessages(auth: String,
                             output: MVPBaseInteractorOutput<SecretMessageResponse>
        )


    }
}