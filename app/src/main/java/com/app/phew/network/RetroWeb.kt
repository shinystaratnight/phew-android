package com.app.phew.network

import com.app.phew.app.AppController
import com.app.phew.preferences.LanguagePrefManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetroWeb {

    private var retrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (retrofit == null) {
                val mLanguagePrefManager =
                        LanguagePrefManager(AppController.mContext)
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            val newRequest = chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("os", "android")
                                    .addHeader("lang", mLanguagePrefManager.appLanguage!!)
                                    .build()
                            chain.proceed(newRequest)
                        }
                        .build()

                retrofit = Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(Urls.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!

        }

    private var googleRetrofit: Retrofit? = null

    val googleClient: Retrofit
        get() {
            if (googleRetrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            val newRequest = chain.request().newBuilder().build()
                            chain.proceed(newRequest)
                        }
                        .build()

                googleRetrofit = Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(Urls.ENDPOINT_GOOGLE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return googleRetrofit!!

        }

    private var moviesRetrofit: Retrofit? = null

    val moviesClient: Retrofit
        get() {
            if (moviesRetrofit == null) {
                val mLanguagePrefManager =
                        LanguagePrefManager(AppController.mContext)
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            val newRequest = chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("os", "android")
                                    .addHeader("lang", mLanguagePrefManager.appLanguage!!)
                                    .build()
                            chain.proceed(newRequest)
                        }
                        .build()

                moviesRetrofit = Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(Urls.MOVIES_END)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return moviesRetrofit!!

        }
}
