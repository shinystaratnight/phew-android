package com.app.phew.ui.secretMessages

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.secretMessages.SecretMessageResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecretMessagesInterActor : SecretMessagesContract.InterActor {

    override fun getSecretMessages(
        auth: String,
        output: MVPBaseInteractorOutput<SecretMessageResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .getSecretMessagesList(auth)
            .enqueue(object : Callback<SecretMessageResponse> {
                override fun onResponse(
                    call: Call<SecretMessageResponse>, response: Response<SecretMessageResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<SecretMessageResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

}