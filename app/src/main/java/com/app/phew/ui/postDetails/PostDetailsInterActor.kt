package com.app.phew.ui.postDetails

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.comment.CommentResponse
import com.app.phew.models.images.ImageModel
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostDetailsInterActor : PostDetailsContract.InterActor, ProgressRequestBody.UploadCallbacks {


    private fun convertAllToMultiPart(images: ArrayList<String>): ArrayList<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>()
        for (image in images) {
            val myImage = File(image)
            val imageBody = ProgressRequestBody(myImage, this)
            parts.add(MultipartBody.Part.createFormData("images[]", myImage.name, imageBody))
        }
        return parts
    }

    override fun onProgressUpdate(percentage: Int) {}
    override fun onError() {}
    override fun onFinish() {}
    override fun getComments(
        auth: String,
        postId: Int,
        output: MVPBaseInteractorOutput<CommentResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).postComments(auth,postId)
            .enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>, response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

    override fun addComment(
        auth: String,
        postId: Int,
        text: String?,
        images: ArrayList<String>?,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        if (images.isNullOrEmpty())
            RetroWeb.client.create(ServiceApi::class.java).addComment(auth,postId,text)
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
        else RetroWeb.client.create(ServiceApi::class.java).addCommentWithMedia(auth,postId,
            convertAllToMultiPart(images) ).enqueue(object : Callback<BaseResponse> {
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

    override fun setPostFavorite(
        auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).setPostFavorite(auth, postId)
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

    override fun reactPost(
        auth: String, postId: Int, type: String, output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).reactPost(auth, postId, type)
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

    override fun deletePost(
        auth: String, postId: Int, output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).deletePost(auth, postId)
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

    override fun findlayPost(
        auth: String,
        postId: Int,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).findlayPost(auth, postId)
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

    override fun updatePostPrivacy(
        auth: String,
        postId: Int,
        showPrivacy: String,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .updatePostPrivacy(auth, postId, showPrivacy = showPrivacy)
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