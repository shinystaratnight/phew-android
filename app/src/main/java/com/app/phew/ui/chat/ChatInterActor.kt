package com.app.phew.ui.chat

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatInterActor : ChatContract.InterActor {
    override fun getChats(auth: String, output: MVPBaseInteractorOutput<ChatsResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .getChatList(auth)
            .enqueue(object : Callback<ChatsResponse> {
                override fun onResponse(
                    call: Call<ChatsResponse>, response: Response<ChatsResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<ChatsResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

}