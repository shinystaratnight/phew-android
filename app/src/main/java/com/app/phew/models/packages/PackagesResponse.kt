package com.app.phew.models.packages

data class PackagesResponse(
    val `data`: ArrayList<PackagesModel>,
    val message: String,
    val status: String
)