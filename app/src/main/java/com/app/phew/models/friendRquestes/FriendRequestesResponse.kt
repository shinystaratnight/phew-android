package com.app.phew.models.friendRquestes

data class FriendRequestesResponse(
    val `data`: ArrayList<FriendRequestesModel>,
    val message: String,
    val status: String
)