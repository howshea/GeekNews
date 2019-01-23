package com.howshea.basemodule.web

import com.howshea.basemodule.AppContext
import com.howshea.basemodule.extentions.ensureDir
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Howshea
 * on 2018/7/13.
 */
private const val GANK_URL = "https://gank.io/api/"

private val cacheFile by lazy {
	File(AppContext.cacheDir, "WebServiceCache").apply {
		ensureDir()
	}
}

val retrofit: Retrofit by lazy {
	Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		.client(OkHttpClient.Builder()
			.connectTimeout(6, TimeUnit.SECONDS)
			.readTimeout(6, TimeUnit.SECONDS)
			.writeTimeout(6, TimeUnit.SECONDS)
			.cache(Cache(cacheFile, 1024 * 1024 * 1024))
			.sslSocketFactory(SSLSocketClient.sslSocketFactory)
			.hostnameVerifier(SSLSocketClient.hostnameVerifier)
			.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
			.build()
		)
		.baseUrl(GANK_URL)
		.build()
}