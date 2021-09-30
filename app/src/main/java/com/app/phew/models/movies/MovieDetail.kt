package com.app.phew.models.movies

import java.io.Serializable

data class MovieDetail(
        val id: Int? = null,
        val adult: Boolean? = null,
        val video: Boolean? = null,
        val original_title: String? = null,
        val genre_ids: ArrayList<Int>? = null,
        val backdrop_path: String? = null,
        val popularity: Double? = null,
        val poster_path: String? = null,
        val title: String? = null,
        val overview: String? = null,
        val original_language: String? = null,
        val vote_count: Int? = null,
        val release_date: String? = null,
        val vote_average: Double? = null,
        val voteCount: Int? = null,
        val releaseDate: String? = null,
        val logoImage: String? = null
) : Serializable