package com.app.phew.models.cities

import java.io.Serializable

/**
 * Created by Mohamed Balsha on 2/24/2021.
 */
data class CityModel(
        val id: Int? = null,
        val country_id: Int? = null,
        val country_name: String? = null,
        val lat: Double? = null,
        val lng: Double? = null,
        val name: String? = null
) : Serializable