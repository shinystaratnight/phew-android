package com.app.phew.models

import java.io.Serializable

data class TokenModel(val token_type: String? = null, val access_token: String? = null) :
    Serializable