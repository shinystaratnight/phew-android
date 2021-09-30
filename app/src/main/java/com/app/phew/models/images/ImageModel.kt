package com.app.phew.models.images

import java.io.Serializable

/**
 * Created by Mohamed Balsha on 2/24/2021.
 */
data class ImageModel(
    val id: Int? = null, val image: String? = null,
    val data: String? = null, val cover_name: String? = null,var type: String? = null
) : Serializable