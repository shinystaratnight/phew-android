package com.app.phew.ui.myFriendes

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.friends.FriendsResponse

class MyFriendsContract {
    interface Presenter : MVPBasePresenter {

        fun getFriends(auth: String,userId:Int)

        fun removeFriend(
                auth: String, userId: Int
        )
    }

    interface View : MVPBaseApiView<FriendsResponse> {
        fun onRemoveFriends(data: BaseResponse)
    }

    interface InterActor {

        fun getFriends(auth: String,userId:Int, output: MVPBaseInteractorOutput<FriendsResponse>)

        fun removeFriend(
                auth: String, userId: Int,
                output: MVPBaseInteractorOutput<BaseResponse>
        )
    }
}