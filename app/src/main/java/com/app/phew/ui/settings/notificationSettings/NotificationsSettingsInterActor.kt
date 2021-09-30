package com.app.phew.ui.settings.notificationSettings

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.secretMessages.SecretMessageResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsSettingsInterActor : NotificationsSettingsContract.InterActor {
    override fun getProfile(
        auth: String,
        userId: Int,
        output: MVPBaseInteractorOutput<LoginResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .getProfile(auth,userId)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

    override fun updateSettings(
        auth: String,
        method: String,
        all_notices: Int?,
        notification_to_new_followers: Int?,
        notification_to_mention: Int?,
        output: MVPBaseInteractorOutput<LoginResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .notificationSettings(auth,method,all_notices,notification_to_new_followers,notification_to_mention)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

    override fun updatePackageSettings(
        auth: String,
        method: String,
        inactive: Int?,
        output: MVPBaseInteractorOutput<LoginResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .updatePackageSettings(auth,method,inactive)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }


}