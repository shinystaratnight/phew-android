package com.app.phew.models

import java.io.Serializable

open class BaseResponse(var status: String? = null, var message: String? = null,var is_active:Boolean?=null) : Serializable