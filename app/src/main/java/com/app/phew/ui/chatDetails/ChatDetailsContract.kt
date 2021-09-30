package com.app.phew.ui.chatDetails

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.chatDetails.ChatDetailsResponse
import com.app.phew.models.chatDetails.SendMessageResponse

class ChatDetailsContract {
    interface Presenter : MVPBasePresenter {
        fun getChatDetails(auth: String, userId: Int)
        fun sendMessage(auth: String, userId: Int, messageType: String, message: String)
    }

    interface View : MVPBaseApiView<ChatDetailsResponse> {
        fun onSendMessageSuccess(response: SendMessageResponse)
    }

    interface InterActor {
        fun getChatDetails(auth: String, userId: Int,
                           output: MVPBaseInteractorOutput<ChatDetailsResponse>
        )

        fun sendMessage(auth: String, userId: Int, messageType: String, message: String,
                        output: MVPBaseInteractorOutput<SendMessageResponse>
        )


    }
}