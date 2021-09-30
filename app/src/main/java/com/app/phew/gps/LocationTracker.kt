package com.app.phew.gps

import android.content.Context
import android.location.Location
import android.util.Log
import com.app.phew.preferences.SharedPrefManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng

class LocationTracker(val context: Context) : BaseGPSTracker(context) {
    private lateinit var locationCallback: LocationCallback
    private var mSharedPrefManager = SharedPrefManager(context)

    init {
        createLocationCallback()
    }

    override fun locationCallback(): LocationCallback = locationCallback

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                locationResult?.lastLocation?.apply { handleLocationResult(this) }
            }
        }
    }

    fun handleLocationResult(currentLocation: Location) = run {
        val currLoc = LatLng(currentLocation.latitude, currentLocation.longitude)
        Log.e("CURRENT_LOCATION", "CurrentLocation: $currLoc")
        mSharedPrefManager.location = currLoc
        currLoc
    }
}