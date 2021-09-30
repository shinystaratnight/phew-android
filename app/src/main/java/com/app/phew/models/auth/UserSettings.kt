package com.app.phew.models.auth

import java.io.Serializable

data class UserSettings(
    val id: Int? = null,
    val all_notices: Int? = null,
    val notification_to_new_followers: Int? = null,
    val notification_to_mention: Int? = null,
    val delete_inactive_followers_and_friends: Int? = null
) : Serializable