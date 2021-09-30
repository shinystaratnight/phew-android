package com.app.phew.models.findly.findlyCities

data class FindlyCitiesResponse(
    val `data`: ArrayList<FindlyCity>,
    val message: String,
    val status: String
)