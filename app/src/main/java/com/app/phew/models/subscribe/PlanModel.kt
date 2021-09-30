package com.app.phew.models.subscribe

import java.io.Serializable

data class PlanModel(
    val characters_post_count: String? = null,
    val profile_images_count: String? = null,
    val friends_count: String? = null,
    val period_to_pin_post_on_findly_by_seconds: String? = null,
    val minimum_period_for_clearing_inactive_accounts_by_days: String? = null
) : Serializable