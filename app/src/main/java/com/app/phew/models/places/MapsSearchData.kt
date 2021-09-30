package com.app.phew.models.places

import java.io.Serializable


/**
 * Created by Mohamed Balsha on 4/6/2021.
 */

data class MapsSearchData(
        val id: String? = null,
        val name: String? = null,
        val formattedAddress: String? = null,
        val geometry: Geometry? = null,
        val icon: String? = null,
        val placeID: String? = null,
        val reference: String? = null,
        val types: ArrayList<String>? = null
) : Serializable