package com.app.phew.ui.createActivity

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.models.places.PlacesResponse
import retrofit2.Response

class CreateActivityPresenter(var view: CreateActivityContract.View?) : CreateActivityContract.Presenter {
    private var mInterActor: CreateActivityContract.InterActor? = CreateActivityInterActor()

    override fun getMovies() {
        mInterActor!!.getMovies(object : MVPBaseInteractorOutput<MoviesResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<MoviesResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseSuccess(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<MoviesResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseError(response.errorBody()!!.string())
                }
            }

            override fun onResponseFailure(t: Throwable) {
                view?.apply {
                    hideProgress()
                    onResponseFailure(t)
                }
            }
        })
    }

    override fun searchMovies(apiKey: String, query: String, page: Int, includeAdult: Boolean) {
        mInterActor!!.searchMovies(apiKey, query, page, includeAdult, object : MVPBaseInteractorOutput<MoviesSearchResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<MoviesSearchResponse>) {
                view?.apply {
                    hideProgress()
                    onSearchSuccess(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<MoviesSearchResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseError(response.errorBody()!!.string())
                }
            }

            override fun onResponseFailure(t: Throwable) {
                view?.apply {
                    hideProgress()
                    onResponseFailure(t)
                }
            }
        })
    }

    override fun searchPlaces(input: String) {
        mInterActor!!.searchPlaces(input, object : MVPBaseInteractorOutput<PlacesResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<PlacesResponse>) {
                view?.apply {
                    hideProgress()
                    onSearchPlaces(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<PlacesResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseError(response.errorBody()!!.string())
                }
            }

            override fun onResponseFailure(t: Throwable) {
                view?.apply {
                    hideProgress()
                    onResponseFailure(t)
                }
            }
        })
    }

    override fun getFriends(auth: String,userId:Int) {
        mInterActor!!.getFriends(auth,userId, object : MVPBaseInteractorOutput<FriendsResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<FriendsResponse>) {
                view?.apply {
                    hideProgress()
                    onGetFriends(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<FriendsResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseError(response.errorBody()!!.string())
                }
            }

            override fun onResponseFailure(t: Throwable) {
                view?.apply {
                    hideProgress()
                    onResponseFailure(t)
                }
            }
        })
    }

    override fun createPost(auth: String, postId: Int?, postType: String, activityType: String, text: String?, location: String?, watching: String?, friendsWith: String?, showPrivacy: String?, showInFindly: Int?, images: ArrayList<String>?, videos: ArrayList<String>?) {
        mInterActor!!.createPost(auth, postId, postType, activityType, text, location, watching,
                friendsWith, showPrivacy, showInFindly, images, videos, object : MVPBaseInteractorOutput<PostResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<PostResponse>) {
                view?.apply {
                    hideProgress()
                    onCreatePost(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<PostResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseError(response.errorBody()!!.string())
                }
            }

            override fun onResponseFailure(t: Throwable) {
                view?.apply {
                    hideProgress()
                    onResponseFailure(t)
                }
            }
        })
    }

    override fun onDestroy() {
        view = null
        mInterActor = null
    }
}