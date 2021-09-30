package com.app.phew.models.chatDetails

data class ChatDetailsModel(
    val ago_time: String,
    val conversation_id: Int,
    val created_at: String,
    val id: Int,
    val message: String,
    val message_position: String,
    val message_type: String,
    val sender_data: SenderData
)