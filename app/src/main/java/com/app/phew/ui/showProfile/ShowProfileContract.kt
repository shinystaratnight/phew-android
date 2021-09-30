package com.app.phew.ui.showProfile

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse

class ShowProfileContract {
    interface Presenter : MVPBasePresenter {
        fun getProfile(auth: String, userId: Int)
        fun follow(auth: String, userId: Int)
        fun sendRequest(auth: String, userId: Int)
        fun cancelRequest(auth: String, userId: Int)
        fun removeFriend(
            auth: String, userId: Int
        )
        fun acceptFriendRequest(auth: String, userId: Int)
        fun rejectFriendRequest(auth: String, userId: Int)
    }

    interface View : MVPBaseApiView<LoginResponse> {
       fun onFollowSuccess(response:BaseResponse)
        fun onAcceptSuccess(response: BaseResponse)
    }

    interface InterActor {
        fun getProfile(
            auth: String, userId: Int,
            output: MVPBaseInteractorOutput<LoginResponse>
        )

        fun follow(auth: String, userId: Int,
            output: MVPBaseInteractorOutput<BaseResponse>
        )
        fun sendRequest(auth: String, userId: Int,
            output: MVPBaseInteractorOutput<BaseResponse>
        )
        fun cancelRequest(auth: String, userId: Int,
            output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun removeFriend(
            auth: String, userId: Int,
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