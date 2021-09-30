package com.app.phew.ui.settings.notificationSettings

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.auth.LoginResponse

class NotificationsSettingsContract {
    interface Presenter : MVPBasePresenter {
        fun getProfile(auth: String, userId: Int)
        fun updateSettings(
            auth: String,
            method: String,
            all_notices: Int?,
            notification_to_new_followers: Int?,
            notification_to_mention: Int?,
        )
        fun updatePackageSettings(
            auth: String,
            method: String,
            inactive: Int?
        )
    }

    interface View : MVPBaseApiView<LoginResponse> {}

    interface InterActor {
        fun getProfile(
            auth: String, userId: Int,
            output: MVPBaseInteractorOutput<LoginResponse>
        )

        fun updateSettings(
            auth: String,
            method: String,
            all_notices: Int?,
            notification_to_new_followers: Int?,
            notification_to_mention: Int?,
            output: MVPBaseInteractorOutput<LoginResponse>
        )

        fun updatePackageSettings(
            auth: String,
            method: String,
            inactive: Int?,
            output: MVPBaseInteractorOutput<LoginResponse>
        )


    }
}