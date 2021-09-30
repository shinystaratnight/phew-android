package com.app.phew.ui.chat

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.chats.ChatsResponse
import retrofit2.Response

class ChatPresenter(var view: ChatContract.View?) : ChatContract.Presenter {
    private var mInterActor: ChatContract.InterActor? = ChatInterActor()
    override fun getChats(auth: String) {
        mInterActor!!.getChats(
            auth,
            object : MVPBaseInteractorOutput<ChatsResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<ChatsResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<ChatsResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseError(response.errorBody()!!.string())
                    }
                }

                override fun onResponseFailure(t: Throwable) {
                    view?.apply {
                        hideProgress()
                        onResponseFailure(t)
                    }
                }
            })
    }


    override fun onDestroy() {
        view = null
        mInterActor = null
    }
}