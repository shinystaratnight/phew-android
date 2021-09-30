package com.app.phew.models

import java.io.Serializable

open class BaseResponseWithPagination(
        var status: String? = null,
        var message: String? = null,
        val meta: BasePaginationMeta? = null
) : Serializable