package com.app.phew.models.chatDetails

data class SenderData(
    val city: City,
    val fullname: String,
    val id: Int,
    val is_follow: Boolean,
    val is_friend: Boolean,
    val profile_image: String,
    val username: String
)