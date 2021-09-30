package com.app.phew.preferences

import android.content.Context
import com.app.phew.app.Constant.SharedPrefKey.App_LANGUAGE
import com.app.phew.app.Constant.SharedPrefKey.SHARED_PREF_NAME
import java.util.*

class LanguagePrefManager(internal var mContext: Context) {

    private val mSharedPreferences =
        mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    var appLanguage: String?
        get() = mSharedPreferences.getString(App_LANGUAGE, Locale.getDefault().language)
        set(language) {
            mSharedPreferences.edit().putString(App_LANGUAGE, language).apply()
        }
}