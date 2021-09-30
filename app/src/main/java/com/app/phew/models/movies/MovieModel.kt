package com.app.phew.models.movies

import java.io.Serializable

data class MovieModel(
        val id: Int? = null,
        val movie_id: String? = null,
        val movie_data: String? = null,
        val movie_detail: MovieDetail? = null,
        val counter: Int? = null
) : Serializable