package com.app.phew.models.places

import com.app.phew.models.BaseResponse

/**
 * Created by Mohamed Balsha on 4/6/2021.
 */

data class PlacesResponse(val results: ArrayList<MapsSearchData>? = null) : BaseResponse()