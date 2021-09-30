package com.app.phew.models.movies

import com.app.phew.models.BaseResponse

data class MoviesResponse(val data: ArrayList<MovieModel>? = null) : BaseResponse()