package com.app.phew.models.secretMessages

data class SecretMessageResponse(
    val `data`: ArrayList<SecretMessageModel>,
    val message: String,
    val status: String
)