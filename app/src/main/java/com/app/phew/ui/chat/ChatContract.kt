package com.app.phew.ui.chat

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.models.notifications.NotificationsResponse

class ChatContract {
    interface Presenter : MVPBasePresenter {
        fun getChats(auth: String)
    }

    interface View : MVPBaseApiView<ChatsResponse> {}

    interface InterActor {
        fun getChats(auth: String,
                             output: MVPBaseInteractorOutput<ChatsResponse>
        )


    }
}