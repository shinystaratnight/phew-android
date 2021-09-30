package com.app.phew.ui.notifications

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.notifications.NotificationsResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsInterActor : NotificationsContract.InterActor {
    override fun getNotifications(auth: String, output: MVPBaseInteractorOutput<NotificationsResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .getNotifications(auth)
                .enqueue(object : Callback<NotificationsResponse> {
                    override fun onResponse(
                            call: Call<NotificationsResponse>, response: Response<NotificationsResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<NotificationsResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }

    override fun deleteNotifications(auth: String, notificationId: String, output: MVPBaseInteractorOutput<BaseResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .deleteNotifications(auth, notificationId)
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

    override fun acceptFriendRequest(auth: String, userId: Int, output: MVPBaseInteractorOutput<BaseResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .acceptFriendRequest(auth, userId)
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

    override fun rejectFriendRequest(auth: String, userId: Int, output: MVPBaseInteractorOutput<BaseResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .rejectFriendRequest(auth, userId)
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