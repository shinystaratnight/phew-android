package com.app.phew.fcm

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

object MyFirebaseInstanceIDService {
    private const val TAG = "FCM Service"

    var deviceToken: String = ""
    fun getToken(context: Context) {
        FirebaseApp.initializeApp(context)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            deviceToken = instanceIdResult.token
            Log.e(TAG, deviceToken)
        }
    }
}