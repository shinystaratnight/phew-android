package com.app.phew.ui.aboutApp

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.aboutApp.AboutAppResponse
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutAppInterActor : AboutAppContract.InterActor {
    override fun getAbout(output: MVPBaseInteractorOutput<AboutAppResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .getAboutApp()
            .enqueue(object : Callback<AboutAppResponse> {
                override fun onResponse(
                    call: Call<AboutAppResponse>, response: Response<AboutAppResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<AboutAppResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

    override fun getTerms(output: MVPBaseInteractorOutput<AboutAppResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .terms()
            .enqueue(object : Callback<AboutAppResponse> {
                override fun onResponse(
                    call: Call<AboutAppResponse>, response: Response<AboutAppResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<AboutAppResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }


}