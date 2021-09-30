package com.app.phew.gps

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

abstract class BaseGPSTracker(private val context: Context) {
    var fusedLocationClient: FusedLocationProviderClient
    private var locationRequest: LocationRequest
    private val locationUpdateInterval: Long = 10000
    private val fastestUpdateInterval: Long = 5000

    init {
        fusedLocationClient = createFusedLocationClient()
        locationRequest = createLocationRequest()
    }

    private fun createLocationRequest(): LocationRequest = LocationRequest.create().apply {
        interval = locationUpdateInterval
        fastestInterval = fastestUpdateInterval
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    abstract fun locationCallback(): LocationCallback

    private fun createFusedLocationClient(): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun startLocationTracking() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback(), Looper.myLooper())
    }

    fun stopLocationTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback())
    }
}