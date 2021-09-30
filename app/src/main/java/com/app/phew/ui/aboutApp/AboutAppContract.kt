package com.app.phew.ui.aboutApp

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.aboutApp.AboutAppResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.models.notifications.NotificationsResponse

class AboutAppContract {
    interface Presenter : MVPBasePresenter {
        fun getAbout()
        fun getTerms()
    }

    interface View : MVPBaseApiView<AboutAppResponse> {}

    interface InterActor {
        fun getAbout(output: MVPBaseInteractorOutput<AboutAppResponse>)
        fun getTerms(output: MVPBaseInteractorOutput<AboutAppResponse>)


    }
}