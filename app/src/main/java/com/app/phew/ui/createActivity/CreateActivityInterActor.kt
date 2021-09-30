package com.app.phew.ui.createActivity

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.models.places.PlacesResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.network.Urls
import com.app.phew.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CreateActivityInterActor : CreateActivityContract.InterActor, ProgressRequestBody.UploadCallbacks {
    override fun getMovies(output: MVPBaseInteractorOutput<MoviesResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).getMovies()
                .enqueue(object : Callback<MoviesResponse> {
                    override fun onResponse(
                            call: Call<MoviesResponse>, response: Response<MoviesResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }

    override fun searchMovies(apiKey: String, query: String, page: Int, includeAdult: Boolean, output: MVPBaseInteractorOutput<MoviesSearchResponse>) {
        output.onServiceRunning()
        RetroWeb.moviesClient.create(ServiceApi::class.java).searchMovies(apiKey, query, page, includeAdult)
                .enqueue(object : Callback<MoviesSearchResponse> {
                    override fun onResponse(
                            call: Call<MoviesSearchResponse>, response: Response<MoviesSearchResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<MoviesSearchResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }

    override fun searchPlaces(input: String, output: MVPBaseInteractorOutput<PlacesResponse>) {
        output.onServiceRunning()
        RetroWeb.googleClient.create(ServiceApi::class.java).searchPlaces(input)
                .enqueue(object : Callback<PlacesResponse> {
                    override fun onResponse(
                            call: Call<PlacesResponse>, response: Response<PlacesResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<PlacesResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }

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

    override fun createPost(auth: String, postId: Int?, postType: String, activityType: String, text: String?, location: String?, watching: String?, friendsWith: String?, showPrivacy: String?, showInFindly: Int?, images: ArrayList<String>?, videos: ArrayList<String>?, output: MVPBaseInteractorOutput<PostResponse>) {
        output.onServiceRunning()
        when {
            !images.isNullOrEmpty() && videos.isNullOrEmpty() -> {
                val mImages = ArrayList<MultipartBody.Part>()
                for (image in images) {
                    val userImage = File(image)
                    val mealBody = ProgressRequestBody(userImage, this)
                    mImages.add(MultipartBody.Part.createFormData("images[]", userImage.name, mealBody))
                }
                RetroWeb.client.create(ServiceApi::class.java).createPostWithMedia(Urls.POSTS,auth, postId,
                        postType, activityType, text, location, watching, friendsWith, showPrivacy,
                        showInFindly, mImages, null)
                        .enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                    call: Call<PostResponse>, response: Response<PostResponse>
                            ) {
                                if (response.isSuccessful)
                                    output.onResponseSuccess(response)
                                else output.onResponseError(response)
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                output.onResponseFailure(t)
                            }
                        })
            }
            images.isNullOrEmpty() && !videos.isNullOrEmpty() -> {
                val mVideos = ArrayList<MultipartBody.Part>()
                for (video in videos) {
                    val userImage = File(video)
                    val mealBody = ProgressRequestBody(userImage, this)
                    mVideos.add(MultipartBody.Part.createFormData("videos[]", userImage.name, mealBody))
                }
                RetroWeb.client.create(ServiceApi::class.java).createPostWithMedia(Urls.POSTS,auth, postId,
                        postType, activityType, text, location, watching, friendsWith, showPrivacy,
                        showInFindly, null, mVideos)
                        .enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                    call: Call<PostResponse>, response: Response<PostResponse>
                            ) {
                                if (response.isSuccessful)
                                    output.onResponseSuccess(response)
                                else output.onResponseError(response)
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                output.onResponseFailure(t)
                            }
                        })
            }
            !images.isNullOrEmpty() && !videos.isNullOrEmpty() -> {
                val mImages = ArrayList<MultipartBody.Part>()
                for (image in images) {
                    val userImage = File(image)
                    val mealBody = ProgressRequestBody(userImage, this)
                    mImages.add(MultipartBody.Part.createFormData("images[]", userImage.name, mealBody))
                }
                val mVideos = ArrayList<MultipartBody.Part>()
                for (video in videos) {
                    val userImage = File(video)
                    val mealBody = ProgressRequestBody(userImage, this)
                    mVideos.add(MultipartBody.Part.createFormData("videos[]", userImage.name, mealBody))
                }
                RetroWeb.client.create(ServiceApi::class.java).createPostWithMedia(Urls.POSTS,auth, postId,
                        postType, activityType, text, location, watching, friendsWith, showPrivacy,
                        showInFindly, mImages, mVideos)
                        .enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                    call: Call<PostResponse>, response: Response<PostResponse>
                            ) {
                                if (response.isSuccessful)
                                    output.onResponseSuccess(response)
                                else output.onResponseError(response)
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                output.onResponseFailure(t)
                            }
                        })
            }
            else -> RetroWeb.client.create(ServiceApi::class.java).createPost(Urls.POSTS,auth, postId, postType,
                    activityType, text, location, watching, friendsWith, showPrivacy, showInFindly)
                    .enqueue(object : Callback<PostResponse> {
                        override fun onResponse(
                                call: Call<PostResponse>, response: Response<PostResponse>
                        ) {
                            if (response.isSuccessful)
                                output.onResponseSuccess(response)
                            else output.onResponseError(response)
                        }

                        override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                            output.onResponseFailure(t)
                        }
                    })
        }
    }

    override fun onProgressUpdate(percentage: Int) {}
    override fun onError() {}
    override fun onFinish() {}
}