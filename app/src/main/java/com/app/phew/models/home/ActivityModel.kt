package com.app.phew.models.home

import java.io.Serializable

/**
 * Created by Mohamed Balsha on 2/28/2021.
 */

data class ActivityModel(
        val id: Int? = null,
        val data: String? = null,
        val cover_name: String? = null
) : Serializable {
    data class WatchingModel(
            val id: Int? = null,
            val adult: Boolean? = null,
            val video: Boolean? = null,
            val original_title: String? = null,
            val genre_ids: ArrayList<Int>? = null,
            val backdrop_path: String? = null,
            val popularity: Float? = null,
            val poster_path: String? = null,
            val title: String? = null,
            val overview: String? = null,
            val original_language: String? = null,
            val vote_count: Int? = null,
            val release_date: String? = null,
            val vote_average: Float? = null,
    ) : Serializable

    data class LocationModel(
            val lat: Float? = null,
            val lng: Float? = null,
            val address: String? = null,
            val name: String? = null
    ) : Serializable
}

/*
*{
*   "id":106646,
*   "adult":false,
*   "video":false,
*   "original_title":"The Wolf of Wall Street",
*   "genre_ids":[80,18,35],
*   "backdrop_path":"/cWUOv3H7YFwvKeaQhoAQTLLpo9Z.jpg",
*   "popularity":89.966999999999999,
*   "poster_path":"/sOxr33wnRuKazR9ClHek73T8qnK.jpg",
*   "title":"The Wolf of Wall Street"
*   "overview":"A New York stockbroker refuses to cooperate in a large securities fraud case involving corruption on Wall Street, corporate banking world and mob infiltration. Based on Jordan Belfort's autobiography.",
*   "original_language":"en",
*   "vote_count":17113,
*   "release_date":"2013-12-25",
*   "vote_average":8
* }
* */

/*
*{
* "lat":21.497057900000001,
* "lng":39.271312500000001,
* "address":"F"
* }
* */