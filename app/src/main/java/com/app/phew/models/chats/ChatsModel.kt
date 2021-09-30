package com.app.phew.models.chats

import java.io.Serializable

data class ChatsModel(
    val ago_time: String,
    val created_at: String,
    val id: Int,
    val last_message: String,
    val message_type: String,
    val other_user_data: OtherUserData,
    val receiver_data: ReceiverData,
    val sender_data: SenderData
):Serializable