package com.app.phew.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.FirebaseApp

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = this
        Fresco.initialize(this)
        FirebaseApp.initializeApp(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        val TAG = AppController::class.java.simpleName
        lateinit var instance: AppController
        lateinit var mContext: Context
        lateinit var levelsEnglish: HashMap<String, String>
        lateinit var levelsArabic: HashMap<String, String>
    }
}