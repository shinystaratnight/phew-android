package com.app.phew.models.notifications

data class NotificationsResponse(
    val `data`: ArrayList<NotificationsModel>,
    val message: String,
    val status: String
)