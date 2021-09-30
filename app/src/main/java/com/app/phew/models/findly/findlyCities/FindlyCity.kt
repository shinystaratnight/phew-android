package com.app.phew.models.findly.findlyCities

data class FindlyCity(
    val country_id: Int,
    val country_name: String,
    val id: Int,
    val lat: Any,
    val like_count: Int,
    val like_type: String,
    val like_type_count: Int,
    val lng: Any,
    val name: String
)