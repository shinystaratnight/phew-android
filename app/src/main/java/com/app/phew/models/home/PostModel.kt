package com.app.phew.models.home

import com.app.phew.models.auth.UserModel
import com.app.phew.models.images.ImageModel
import java.io.Serializable

data class PostModel(
        val activity_type: String? = null,
        val comments_count: Int,
        val created_ago: String,
        val created_at: String,
        val desc: String,
        val file: String,
        val id: Int,
        val images: ArrayList<ImageModel>,
        var is_fav: Boolean,
        var is_like: Boolean = false,
        var like_type: String? = null,
        var likes_count: Int = 0,
        val location: ActivityModel,
        val mentions: ArrayList<UserModel>,
        val post_type: String,
        val postable: PostModel? = null,
        val postable_type: String,
        val screen_shots: List<UserModel>,
        val show_privacy: String,
        val sponsor: Sponsor,
        val text: String,
        val thumbnail: String,
        val message: String? = null,
        val ago_time:String?=null,
        val type: String,
        val url: String,
        val user: UserModel,
        val videos: ArrayList<ImageModel>,
        val watching: ActivityModel
) : Serializable