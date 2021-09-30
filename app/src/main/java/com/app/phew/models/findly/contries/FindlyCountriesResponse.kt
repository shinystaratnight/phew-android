package com.app.phew.models.findly.contries

data class FindlyCountriesResponse(
    val `data`: ArrayList<FindlyCountry>,
    val message: String,
    val status: String
)