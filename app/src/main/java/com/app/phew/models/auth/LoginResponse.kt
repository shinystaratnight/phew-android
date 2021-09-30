package com.app.phew.models.auth

import com.app.phew.models.BaseResponse

data class LoginResponse(val data: UserModel? = null) : BaseResponse()