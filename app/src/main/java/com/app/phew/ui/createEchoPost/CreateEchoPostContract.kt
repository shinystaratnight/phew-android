package com.app.phew.ui.createEchoPost

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse

class CreateEchoPostContract {
    interface Presenter : MVPBasePresenter {
        fun createEchoPost(auth: String, postId: Int, text: String)
    }

    interface View : MVPBaseApiView<BaseResponse>

    interface InterActor {
        fun createEchoPost(
                auth: String, postId: Int, text: String,
                output: MVPBaseInteractorOutput<BaseResponse>
        )
    }
}