package com.app.phew.models.chatDetails

data class SendMessageResponse(
    val `data`: ChatDetailsModel,
    val message: String,
    val status: String
)