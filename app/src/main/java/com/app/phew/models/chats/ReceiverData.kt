package com.app.phew.models.chats

import com.app.phew.models.notifications.City
import java.io.Serializable

data class ReceiverData(
    val city: City,
    val fullname: String,
    val id: Int,
    val is_follow: Boolean,
    val is_friend: Boolean,
    val profile_image: String,
    val username: String
): Serializable