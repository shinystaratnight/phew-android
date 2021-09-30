package com.app.phew.ui.friendRequest

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.friendRquestes.FriendRequestesResponse
import com.app.phew.models.notifications.NotificationsResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendRequestInterActor : FriendRequestContract.InterActor {
    override fun getRequests(
        auth: String,
        userId: Int,
        filter:String,

        output: MVPBaseInteractorOutput<FriendRequestesResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .getFriendRequestsList(auth,userId,filter)
            .enqueue(object : Callback<FriendRequestesResponse> {
                override fun onResponse(
                    call: Call<FriendRequestesResponse>, response: Response<FriendRequestesResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<FriendRequestesResponse>, t: Throwable) {
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