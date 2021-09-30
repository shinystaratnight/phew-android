package com.app.phew.ui.showProfile

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowProfileInterActor : ShowProfileContract.InterActor {

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

    override fun follow(auth: String, userId: Int, output: MVPBaseInteractorOutput<BaseResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .follow(auth,userId)
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

    override fun sendRequest(
        auth: String,
        userId: Int,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .sendRequest(auth,userId)
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

    override fun cancelRequest(
        auth: String,
        userId: Int,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .cancelRequest(auth,userId)
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

    override fun removeFriend(auth: String, userId: Int, output: MVPBaseInteractorOutput<BaseResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).removeFriend(auth,userId)
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