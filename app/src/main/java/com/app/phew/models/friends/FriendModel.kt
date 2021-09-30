package com.app.phew.models.friends

import com.app.phew.models.auth.UserModel
import java.io.Serializable

/**
 * Created by Mohamed Balsha on 3/28/2021.
 */

data class FriendModel(val user: UserModel? = null, val date: String? = null) : Serializable
