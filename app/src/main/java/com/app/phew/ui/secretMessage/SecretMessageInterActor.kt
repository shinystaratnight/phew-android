package com.app.phew.ui.secretMessage

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecretMessageInterActor : SecretMessageContract.InterActor {
    override fun sendSecretMessage(
        auth: String,
        userId: Int,
        message: String?,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .sendSecretMessage(auth,userId,message!!)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>, response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }


}