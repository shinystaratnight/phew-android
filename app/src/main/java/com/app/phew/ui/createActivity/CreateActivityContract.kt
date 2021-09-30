package com.app.phew.ui.createActivity

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.models.places.PlacesResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Query

class CreateActivityContract {
    interface Presenter : MVPBasePresenter {
        fun getMovies()
        fun searchMovies(
                apiKey: String = "b9aa09eb38643436e7f8e12a1ba2e953",
                query: String, page: Int, includeAdult: Boolean = false
        )

        fun searchPlaces(input: String)

        fun getFriends(auth: String,userId:Int)

        fun createPost(
                auth: String, postId: Int?, postType: String, activityType: String, text: String?,
                location: String?, watching: String?, friendsWith: String?, showPrivacy: String?,
                showInFindly: Int?, images: ArrayList<String>?, videos: ArrayList<String>?
        )
    }

    interface View : MVPBaseApiView<MoviesResponse> {
        fun onSearchSuccess(data: MoviesSearchResponse)
        fun onSearchPlaces(data: PlacesResponse)
        fun onGetFriends(data: FriendsResponse)
        fun onCreatePost(data: PostResponse)
    }

    interface InterActor {
        fun getMovies(output: MVPBaseInteractorOutput<MoviesResponse>)
        fun searchMovies(
                apiKey: String = "b9aa09eb38643436e7f8e12a1ba2e953", query: String, page: Int,
                includeAdult: Boolean = false, output: MVPBaseInteractorOutput<MoviesSearchResponse>
        )

        fun searchPlaces(input: String, output: MVPBaseInteractorOutput<PlacesResponse>)

        fun getFriends(auth: String,userId:Int, output: MVPBaseInteractorOutput<FriendsResponse>)

        fun createPost(
                auth: String, postId: Int?, postType: String, activityType: String, text: String?,
                location: String?, watching: String?, friendsWith: String?, showPrivacy: String?,
                showInFindly: Int?, images: ArrayList<String>?, videos: ArrayList<String>?,
                output: MVPBaseInteractorOutput<PostResponse>
        )
    }
}