package com.app.phew.models.subscribe

import java.io.Serializable

data class PackageModel(
    val id: Int? = null,
    val package_type: String? = null,
    val price: Float? = null,
    val period: String? = null,
    val period_type: String? = null,
    val plan: PlanModel? = null
) : Serializable