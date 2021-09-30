package com.app.phew.models.followUs

import com.app.phew.models.followUs.FollowUsModel

data class FollowUsResponse(
    val `data`: FollowUsModel,
    val message: String,
    val status: String
)