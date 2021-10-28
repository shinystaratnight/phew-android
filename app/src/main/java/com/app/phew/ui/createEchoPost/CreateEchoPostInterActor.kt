package com.app.phew.ui.createEchoPost

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.utils.ProgressRequestBody
import com.app.phew.models.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateEchoPostInterActor : CreateEchoPostContract.InterActor, ProgressRequestBody.UploadCallbacks {
    override fun createEchoPost(
            auth: String, postId: Int, text: String, output: MVPBaseInteractorOutput<BaseResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).echoWithComment(auth, postId, text)
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

    override fun onProgressUpdate(percentage: Int) {}
    override fun onError() {}
    override fun onFinish() {}
}