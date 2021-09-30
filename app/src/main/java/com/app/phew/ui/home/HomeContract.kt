package com.app.phew.ui.home

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.home.HomeResponse
import com.app.phew.models.home.ScreenShotBody

class HomeContract {
    interface Presenter : MVPBasePresenter {
        fun getHome(url: String, auth: String, type: String, page: Int)
        fun deletePost(auth: String, postId: Int)
        fun findlayPost(auth: String, postId: Int)
        fun updatePostPrivacy(auth: String, postId: Int, showPrivacy: String)
        fun setPostFavorite(auth: String, postId: Int)
        fun reactPost(auth: String, postId: Int, type: String)
        fun screenShot(auth: String, screenShotBody: ScreenShotBody)
    }

    interface View : MVPBaseApiView<HomeResponse> {
        fun onPostUpdate(response: BaseResponse)
    }

    interface InterActor {
        fun getHome(
            url: String, auth: String, type: String, page: Int,
            output: MVPBaseInteractorOutput<HomeResponse>
        )

        fun deletePost(auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>)

        fun findlayPost(auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>)
        fun updatePostPrivacy(
            auth: String, postId: Int, showPrivacy: String,
            output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun setPostFavorite(
            auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun reactPost(
            auth: String, postId: Int, type: String, output: MVPBaseInteractorOutput<BaseResponse>
        )

        fun screenShot(
            auth: String, screenShotBody: ScreenShotBody,
            output: MVPBaseInteractorOutput<BaseResponse>
        )
    }
}