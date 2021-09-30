package com.app.phew.ui.notifications

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.notifications.NotificationsResponse

class NotificationsContract {
    interface Presenter : MVPBasePresenter {
        fun getNotifications(auth: String)
        fun deleteNotifications(auth: String, notificationId: String)
        fun acceptFriendRequest(auth: String, userId: Int)
        fun rejectFriendRequest(auth: String, userId: Int)
    }

    interface View : MVPBaseApiView<NotificationsResponse> {
        fun showFieldError(field: String)
        fun onDeleteSuccess(response: BaseResponse)
        fun onAcceptSuccess(response: BaseResponse)
    }

    interface InterActor {
        fun getNotifications(auth: String,
                             output: MVPBaseInteractorOutput<NotificationsResponse>
        )

        fun deleteNotifications(auth: String, notificationId: String,
                                output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun acceptFriendRequest(auth: String, userId: Int,
                                output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun rejectFriendRequest(auth: String, userId: Int,
                                output: MVPBaseInteractorOutput<BaseResponse>
        )


    }
}