package com.app.phew.models.packages

data class PackagesModel(
    val id: Int,
    val package_type: String,
    val period: String,
    val period_type: String,
    val plan: Plan,
    val price: Int,
    var isSelected: Boolean=false,
)