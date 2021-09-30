package com.app.phew.models

import java.io.Serializable

data class BasePaginationMeta(
        val current_page: Int? = null,
        val from: Int? = null,
        val last_page: Int? = null,
        val path: String? = null,
        val per_page: Int? = null,
        val to: Int? = null,
        val total: Int? = null
) : Serializable