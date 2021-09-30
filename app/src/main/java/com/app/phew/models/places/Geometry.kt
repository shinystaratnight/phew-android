package com.app.phew.models.places

import java.io.Serializable

/**
 * Created by Mohamed Balsha on 4/6/2021.
 */

data class Geometry(val location: Location? = null, val viewport: ViewPort? = null) : Serializable