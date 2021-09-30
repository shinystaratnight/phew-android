package com.app.phew.models.countries

import com.app.phew.models.BaseResponse

data class CountriesResponse(
    val `data`: ArrayList<CountryModel>,
):BaseResponse()