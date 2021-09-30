package com.app.phew.models.comment

import com.app.phew.models.images.ImageModel

data class CommentModel(
    val id: Int,
    val images: ArrayList<ImageModel>,
    val postable: Boolean,
    val text: String,
    val user: User
)