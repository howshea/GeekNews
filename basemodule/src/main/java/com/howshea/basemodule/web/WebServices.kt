package com.howshea.basemodule.web

import com.howshea.basemodule.AppContext
import com.howshea.basemodule.extentions.ensureDir
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Howshea
 * on 2018/7/13.
 */
private const val BASE_URL = "http://gank.io/api"

private val cacheFile by lazy {
    File(AppContext.cacheDir, "WebServiceCache").apply {
        ensureDir()
    }
}

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(OkHttpClient.Builder()
            .connectTimeout(6, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .writeTimeout(6, TimeUnit.SECONDS)
            .cache(Cache(cacheFile, 1024 * 1024 * 1024))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        )
        .baseUrl(BASE_URL)
        .build()
}