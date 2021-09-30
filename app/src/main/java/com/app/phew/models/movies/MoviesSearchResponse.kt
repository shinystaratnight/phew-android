package com.app.phew.models.movies

import java.io.Serializable

data class MoviesSearchResponse(
        val page: Int,
        val results: ArrayList<MovieDetail>,
        val total_pages: Int,
        val total_results: Int
) : Serializable