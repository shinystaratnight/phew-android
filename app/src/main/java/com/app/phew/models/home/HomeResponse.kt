package com.app.phew.models.home

import com.app.phew.models.BaseResponseWithPagination

data class HomeResponse(val data: ArrayList<HomeModel>) : BaseResponseWithPagination()