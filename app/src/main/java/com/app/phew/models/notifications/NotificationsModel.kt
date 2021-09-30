package com.app.phew.models.notifications

data class NotificationsModel(
    val body: String,
    val created_at: String,
    val created_time: String,
    val id: String,
    val key: String,
    val key_id: Int,
    val key_type: String,
    val sender_data: SenderData?,
    val title: String
)