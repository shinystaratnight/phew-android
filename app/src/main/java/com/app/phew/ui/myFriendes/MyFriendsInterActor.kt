package com.app.phew.ui.myFriendes

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MyFriendsInterActor : MyFriendsContract.InterActor {


    override fun getFriends(auth: String,userId:Int, output: MVPBaseInteractorOutput<FriendsResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).getFriends(auth,userId)
                .enqueue(object : Callback<FriendsResponse> {
                    override fun onResponse(
                            call: Call<FriendsResponse>, response: Response<FriendsResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<FriendsResponse>, t: Throwable) {
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


}