package com.app.phew.ui.friendRequest

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.friendRquestes.FriendRequestesResponse
import com.app.phew.models.notifications.NotificationsResponse

class FriendRequestContract {
    interface Presenter : MVPBasePresenter {
        fun getRequests(auth: String,userId: Int,filter:String)
        fun acceptFriendRequest(auth: String, userId: Int)
        fun rejectFriendRequest(auth: String, userId: Int)
    }

    interface View : MVPBaseApiView<FriendRequestesResponse> {
        fun showFieldError(field: String)
        fun onAcceptSuccess(response: BaseResponse)
    }

    interface InterActor {
        fun getRequests(auth: String,userId: Int,filter:String,
                             output: MVPBaseInteractorOutput<FriendRequestesResponse>
        )

        fun acceptFriendRequest(auth: String, userId: Int,
                                output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun rejectFriendRequest(auth: String, userId: Int,
                                output: MVPBaseInteractorOutput<BaseResponse>
        )


    }
}