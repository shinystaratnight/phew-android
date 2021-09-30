package com.app.phew.models.friends

import com.app.phew.models.BaseResponse

/**
 * Created by Mohamed Balsha on 3/28/2021.
 */

data class FriendsResponse(val data: ArrayList<FriendModel>? = null) : BaseResponse()