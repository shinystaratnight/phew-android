package com.app.phew.models.countries

import java.io.Serializable

/**
 * Created by Mohamed Balsha on 2/24/2021.
 */

data class CountryModel(
    val id: Int? = null,
    val name: String? = null,
    val short_name: String? = null,
    val flag: String? = null,
    val show_phonecode: String? = null,
    val phonecode: String? = null,
    val continent: String? = null
) : Serializable