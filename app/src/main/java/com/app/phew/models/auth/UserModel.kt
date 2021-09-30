package com.app.phew.models.auth

import com.app.phew.models.TokenModel
import com.app.phew.models.cities.CityModel
import com.app.phew.models.images.ImageModel
import com.app.phew.models.nationalities.NationalityModel
import com.app.phew.models.subscribe.SubscribeModel
import java.io.Serializable

data class UserModel(
        val id: Int? = null,
        val username: String? = null,
        val fullname: String? = null,
        val mobile: String? = null,
        val email: String? = null,
        val gender: String? = null,
        val date_of_birth: String? = null,
        val profile_image: String? = null,
        val profile_images: ArrayList<ImageModel>? = null,
        val cover: String? = null,
        val is_verified: Boolean? = null,
        val is_follow: Boolean? = null,
        val follower_count: Int? = null,
        val following_count: Int? = null,
        val is_friend: Boolean? = null,
        val is_friend_request: Boolean? = null,
        val sender_friend_request: String? = null,
        val friends_count: Int? = null,
        val posts_count: Int? = null,
        val is_subscribed: Boolean? = null,
        val subscribe_data: SubscribeModel? = null,
        val user_settings: UserSettings? = null,
        val token: TokenModel? = null,
        val city: CityModel? = null,
        val nationality: NationalityModel? = null,
        val lat: Double? = null,
        val lng: Double? = null,
) : Serializable