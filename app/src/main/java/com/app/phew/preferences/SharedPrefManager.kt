package com.app.phew.preferences

import android.content.Context
import com.app.phew.app.Constant
import com.app.phew.app.Constant.SharedPrefKey.APP_LANGUAGE
import com.app.phew.app.Constant.SharedPrefKey.DEVICE_TOKEN
import com.app.phew.app.Constant.SharedPrefKey.FIRST_TIME
import com.app.phew.app.Constant.SharedPrefKey.GUEST_STATUS
import com.app.phew.app.Constant.SharedPrefKey.LOCATION
import com.app.phew.app.Constant.SharedPrefKey.LOGIN_STATUS
import com.app.phew.app.Constant.SharedPrefKey.MOBILE_TYPE
import com.app.phew.app.Constant.SharedPrefKey.RING_TONE
import com.app.phew.app.Constant.SharedPrefKey.SHARED_PREF_NAME
import com.app.phew.app.Constant.SharedPrefKey.TOKEN
import com.app.phew.app.Constant.SharedPrefKey.USER
import com.app.phew.app.Constant.SharedPrefKey.VIBRATION
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.app.phew.app.Constant.SharedPrefKey.NOTIFICATIONS_COUNT
import com.app.phew.models.auth.UserModel

class SharedPrefManager(internal var mContext: Context) {

    private var mSharedPreferences =
        mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    var loginStatus: Boolean
        get() = mSharedPreferences.getBoolean(LOGIN_STATUS, false)
        set(status) {
            mSharedPreferences.edit().putBoolean(LOGIN_STATUS, status).apply()
        }

    var firstTime: Boolean
        get() = mSharedPreferences.getBoolean(FIRST_TIME, false)
        set(status) {
            mSharedPreferences.edit().putBoolean(FIRST_TIME, status).apply()
        }

    var appLanguage: String
        get() = mSharedPreferences.getString(APP_LANGUAGE, "en") ?: "en"
        set(appLanguage) {
            mSharedPreferences.edit().putString(APP_LANGUAGE, appLanguage).apply()
        }

    var mobileType: String
        get() = mSharedPreferences.getString(MOBILE_TYPE, "android") ?: "android"
        set(mobileType) {
            mSharedPreferences.edit().putString(MOBILE_TYPE, mobileType).apply()
        }

    var userData: UserModel
        get() = Gson().fromJson(mSharedPreferences.getString(USER, null), UserModel::class.java)
        set(userModel) {
            mSharedPreferences.edit().putString(USER, Gson().toJson(userModel)).apply()
        }

    var notificationsCount: Int
        get() = mSharedPreferences.getInt(NOTIFICATIONS_COUNT, 0)
        set(notificationsCount) {
            mSharedPreferences.edit().putInt(NOTIFICATIONS_COUNT, notificationsCount).apply()
        }

    var location: LatLng
        get() = Gson().fromJson(
            mSharedPreferences.getString(LOCATION, "{(0.0:0.0)}"),
            LatLng::class.java
        )
        set(location) {
            mSharedPreferences.edit().putString(LOCATION, Gson().toJson(location)).apply()
        }

    var deviceToken: String?
        get() = mSharedPreferences.getString(DEVICE_TOKEN, "")
        set(device_token) {
            mSharedPreferences.edit().putString(DEVICE_TOKEN, device_token).apply()
        }

    var authToken: String?
        get() = mSharedPreferences.getString(TOKEN, "")
        set(authToken) {
            mSharedPreferences.edit().putString(TOKEN, authToken).apply()
        }

    fun <T> putValue(key: String, value: T) {
        when (value) {
            is Int -> mSharedPreferences.edit().putInt(key, value).apply()
            is Float -> mSharedPreferences.edit().putFloat(key, value).apply()
            is String -> mSharedPreferences.edit().putString(key, value).apply()
            is Boolean -> mSharedPreferences.edit().putBoolean(key, value).apply()
            else -> throw IllegalArgumentException("Only primitive data types can be stored in SharedPreferences")
        }
    }


    fun <T> getValue(key: String, defValue: T): T {
        return when (defValue) {
            is String -> mSharedPreferences.getString(key, defValue as String) as T
            is Int -> mSharedPreferences.getInt(key, defValue as Int) as T
            is Float -> mSharedPreferences.getFloat(key, defValue as Float) as T
            is Long -> mSharedPreferences.getLong(key, defValue as Long) as T
            is Boolean -> mSharedPreferences.getBoolean(key, defValue as Boolean) as T
            else -> throw IllegalArgumentException("Only primitive data types can be stored in SharedPreferences")
        }

    }

    var guest: Boolean
        get() = mSharedPreferences.getBoolean(GUEST_STATUS, false)
        set(guest) {
            mSharedPreferences.edit().putBoolean(GUEST_STATUS, guest).apply()
        }

    var isNotification: Boolean
        get() = mSharedPreferences.getBoolean(Constant.SharedPrefKey.IS_nOTIFICATION, false)
        set(isNotification) {
            mSharedPreferences.edit()
                .putBoolean(Constant.SharedPrefKey.IS_nOTIFICATION, isNotification).apply()
        }

    var myLat: String
        get() = mSharedPreferences.getString(Constant.SharedPrefKey.MY_LAT, "0.0").toString()
        set(myLat) {
            mSharedPreferences.edit().putString(Constant.SharedPrefKey.MY_LAT, myLat).apply()
        }

    var myLng: String
        get() = mSharedPreferences.getString(Constant.SharedPrefKey.MY_LNG, "0.0").toString()
        set(myLng) {
            mSharedPreferences.edit().putString(Constant.SharedPrefKey.MY_LNG, myLng).apply()
        }

    var vibration: Boolean
        get() = mSharedPreferences.getBoolean(VIBRATION, false)
        set(vibration) {
            mSharedPreferences.edit().putBoolean(VIBRATION, vibration).apply()
        }

    var ringTone: Boolean
        get() = mSharedPreferences.getBoolean(RING_TONE, false)
        set(ringTone) {
            mSharedPreferences.edit().putBoolean(RING_TONE, ringTone).apply()
        }

    fun logout() {
        mSharedPreferences.edit().clear().apply()
    }
}