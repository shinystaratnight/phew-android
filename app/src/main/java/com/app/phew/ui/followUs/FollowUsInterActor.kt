package com.app.phew.ui.followUs


import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.followUs.FollowUsResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.ui.followUs.FollowUsContract

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowUsInterActor : FollowUsContract.InterActor {
    override fun followUs(output: MVPBaseInteractorOutput<FollowUsResponse>) {
        output.onServiceRunning()
        RetroWeb
            .client
            .create(ServiceApi::class.java)
            .followUs()
            .enqueue(object : Callback<FollowUsResponse> {

                override fun onResponse(
                    call: Call<FollowUsResponse>,
                    response: Response<FollowUsResponse>
                ) {
                    if (response.isSuccessful){
                        output.onResponseSuccess(response)
                    }
                    else {
                        output.onResponseError(response)
                    }
                }
                override fun onFailure(call: Call<FollowUsResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }

            })
    }


}