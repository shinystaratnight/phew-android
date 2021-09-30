package com.app.phew.ui.chatDetails

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.chatDetails.ChatDetailsResponse
import com.app.phew.models.chatDetails.SendMessageResponse
import com.app.phew.models.friendRquestes.FriendRequestesResponse
import com.app.phew.models.notifications.NotificationsResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ChatDetailsInterActor : ChatDetailsContract.InterActor, ProgressRequestBody.UploadCallbacks {
    override fun getChatDetails(auth: String, userId: Int, output: MVPBaseInteractorOutput<ChatDetailsResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .chatDetails(auth,userId)
                .enqueue(object : Callback<ChatDetailsResponse> {
                    override fun onResponse(
                            call: Call<ChatDetailsResponse>, response: Response<ChatDetailsResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<ChatDetailsResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }

    override fun sendMessage(auth: String, userId: Int, messageType: String, message: String, output: MVPBaseInteractorOutput<SendMessageResponse>) {
       if (messageType=="text"){
           output.onServiceRunning()
           RetroWeb.client.create(ServiceApi::class.java)
                   .sendMessage(auth,userId,messageType,message)
                   .enqueue(object : Callback<SendMessageResponse> {
                       override fun onResponse(
                               call: Call<SendMessageResponse>, response: Response<SendMessageResponse>
                       ) {
                           if (response.isSuccessful)
                               output.onResponseSuccess(response)
                           else output.onResponseError(response)
                       }

                       override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                           output.onResponseFailure(t)
                       }
                   })
       }else{
           output.onServiceRunning()
           RetroWeb.client.create(ServiceApi::class.java)
                   .sendMediaMessage(auth,userId,messageType,convertToMultiPart(message,"message"))
                   .enqueue(object : Callback<SendMessageResponse> {
                       override fun onResponse(
                               call: Call<SendMessageResponse>, response: Response<SendMessageResponse>
                       ) {
                           if (response.isSuccessful)
                               output.onResponseSuccess(response)
                           else output.onResponseError(response)
                       }

                       override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                           output.onResponseFailure(t)
                       }
                   })
       }
    }

    fun convertToMultiPart(image: String, tag: String): MultipartBody.Part {
        val myImage = File(image)
        val imageBody = ProgressRequestBody(myImage, this)
        return MultipartBody.Part.createFormData(tag, myImage.name, imageBody)
    }

    override fun onProgressUpdate(percentage: Int) {}

    override fun onError() {}

    override fun onFinish() {}

}