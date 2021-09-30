package com.app.phew.gps

import com.google.android.gms.location.LocationResult

interface locationListener {
    fun locationResponse(locationResult: LocationResult)
}