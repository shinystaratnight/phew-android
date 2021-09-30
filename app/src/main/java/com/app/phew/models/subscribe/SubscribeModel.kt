package com.app.phew.models.subscribe

import java.io.Serializable

/**
 * Created by Mohamed Balsha on 2/24/2021.
 */
data class SubscribeModel(
    val id: Int? = null,
    val `package`: PackageModel? = null,
    val package_type: String? = null,
    val subscription_start_date: String? = null,
    val subscription_end_date: String? = null
) : Serializable