package com.app.phew.models.chats

import java.io.Serializable

data class ChatsResponse(
    val `data`: ArrayList<ChatsModel>,
    val message: String,
    val status: String
): Serializable