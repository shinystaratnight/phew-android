package com.app.phew.models.comment

data class CommentResponse(
    val `data`: ArrayList<CommentModel>,
    val message: String,
    val status: String
)