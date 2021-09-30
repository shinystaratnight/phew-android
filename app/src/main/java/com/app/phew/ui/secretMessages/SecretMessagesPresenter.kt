package com.app.phew.ui.secretMessages

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.models.secretMessages.SecretMessageResponse
import retrofit2.Response

class SecretMessagesPresenter(var view: SecretMessagesContract.View?) : SecretMessagesContract.Presenter {
    private var mInterActor: SecretMessagesContract.InterActor? = SecretMessagesInterActor()
    override fun getSecretMessages(auth: String) {
        mInterActor!!.getSecretMessages(
            auth,
            object : MVPBaseInteractorOutput<SecretMessageResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<SecretMessageResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }

                override fun onResponseError(response: Response<SecretMessageResponse>) {
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