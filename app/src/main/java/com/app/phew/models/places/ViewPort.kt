package com.app.phew.models.places

import java.io.Serializable

/**
 * Created by Mohamed Balsha on 4/6/2021.
 */

data class ViewPort(val northeast: Location? = null, val southwest: Location? = null) : Serializable