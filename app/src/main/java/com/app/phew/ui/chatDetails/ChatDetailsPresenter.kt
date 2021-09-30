package com.app.phew.ui.chatDetails

import com.app.phew.base.BaseFragment
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.chatDetails.ChatDetailsResponse
import com.app.phew.models.chatDetails.SendMessageResponse
import com.app.phew.models.friendRquestes.FriendRequestesResponse
import com.app.phew.models.notifications.NotificationsResponse
import retrofit2.Response

class ChatDetailsPresenter(var view: ChatDetailsContract.View?) : ChatDetailsContract.Presenter {
    private var mInterActor: ChatDetailsContract.InterActor? = ChatDetailsInterActor()



    override fun getChatDetails(auth: String, userId: Int) {
        mInterActor!!.getChatDetails(
                auth,userId,
                object : MVPBaseInteractorOutput<ChatDetailsResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<ChatDetailsResponse>) {
                        view?.apply {
                            hideProgress()
                            onResponseSuccess(response.body()!!)
                        }
                    }

                    override fun onResponseError(response: Response<ChatDetailsResponse>) {
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

    override fun sendMessage(auth: String, userId: Int, messageType: String, message: String) {
        mInterActor!!.sendMessage(
                auth,userId,messageType,message,
                object : MVPBaseInteractorOutput<SendMessageResponse> {
                    override fun onServiceRunning() {
                        view?.showProgress()
                    }

                    override fun onResponseSuccess(response: Response<SendMessageResponse>) {
                        view?.apply {
                            hideProgress()
                            onSendMessageSuccess(response.body()!!)
                        }
                    }

                    override fun onResponseError(response: Response<SendMessageResponse>) {
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