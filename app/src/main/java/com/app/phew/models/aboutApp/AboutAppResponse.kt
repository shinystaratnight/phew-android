package com.app.phew.models.aboutApp

data class AboutAppResponse(
    val `data`: AboutAppModel,
    val message: String,
    val status: String
)