package com.app.phew.ui.settings.editProfile

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.images.ImageModel
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProfileInterActor : EditProfileContract.InterActor, ProgressRequestBody.UploadCallbacks {


    private fun convertAllToMultiPart(images: ArrayList<ImageModel>): ArrayList<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>()
        for (image in images) {
            val myImage = File(image.image!!)
            val imageBody = ProgressRequestBody(myImage, this)
            parts.add(MultipartBody.Part.createFormData("avatar[]", myImage.name, imageBody))
        }
        return parts
    }

    override fun onProgressUpdate(percentage: Int) {}
    override fun onError() {}
    override fun onFinish() {}
    override fun updateProfile(auth: String, fullname: String, mobile: String, email: String, images: ArrayList<ImageModel>?, output: MVPBaseInteractorOutput<LoginResponse>) {
        output.onServiceRunning()
        if (images.isNullOrEmpty())
            RetroWeb.client.create(ServiceApi::class.java).updateProfileWithoutImages(auth,"PUT", fullname, mobile, email)
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
        else RetroWeb.client.create(ServiceApi::class.java).updateProfileWithImages(auth,"PUT",
                fullname, mobile, email,
                convertAllToMultiPart(images) ).enqueue(object : Callback<LoginResponse> {
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

    override fun deletePicture(auth: String, id: Int, output: MVPBaseInteractorOutput<BaseResponse>) {
        RetroWeb.client.create(ServiceApi::class.java).deleteImage(auth, id)
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