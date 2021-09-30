package com.app.phew.models.chatDetails

data class ChatDetailsResponse(
    val `data`: ArrayList<ChatDetailsModel>,
    val message: String,
    val status: String
)