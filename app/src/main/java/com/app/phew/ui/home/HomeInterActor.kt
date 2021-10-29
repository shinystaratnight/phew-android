package com.app.phew.ui.home

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.home.HomeResponse
import com.app.phew.models.home.ScreenShotBody
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeInterActor : HomeContract.InterActor {
    override fun getHome(
        url: String, auth: String, type: String, page: Int,
        output: MVPBaseInteractorOutput<HomeResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .getHome(url, auth, type, page)
            .enqueue(object : Callback<HomeResponse> {
                override fun onResponse(
                    call: Call<HomeResponse>, response: Response<HomeResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
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

    override fun screenShot(
        auth: String, screenShotBody: ScreenShotBody, output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).screenShot(auth, screenShotBody)
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

    override fun echoWithoutComment(
        auth: String,
        postId: Int,
        showPrivacy: String,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .echoWithoutComment(auth, postId, showPrivacy)
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

    override fun echoWithComment(
        auth: String,
        postId: Int,
        commentText: String,
        output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .echoWithComment(auth, postId, commentText)
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

    override fun searchMovie(query: String, output: MVPBaseInteractorOutput<MoviesSearchResponse>) {
        output.onServiceRunning()
        RetroWeb.moviesClient.create(ServiceApi::class.java).searchMovies("b9aa09eb38643436e7f8e12a1ba2e953", query, 1)
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
}