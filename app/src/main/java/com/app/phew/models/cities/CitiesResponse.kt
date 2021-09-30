package com.app.phew.models.cities

import com.app.phew.models.BaseResponse

data class CitiesResponse(
    val `data`: ArrayList<CityModel>,
):BaseResponse()