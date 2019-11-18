package com.himanshu.project.myapplication.services.api

import android.app.Application
import com.himanshu.project.myapplication.BuildConfig
import com.himanshu.project.myapplication.Swivel
import com.himanshu.project.myapplication.data.model.News
import com.himanshu.project.myapplication.services.network.InternetConnection
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import java.util.concurrent.TimeUnit

interface APIInterface {
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String, @Query("category") category: String, @Query("apiKey") apiKey: String): News

    @GET("everything")
    suspend fun getCustomNews(@Query("q") userPreferences: String, @Query("from") from: String, @Query("sortBy") sortBy: String, @Query("apiKey") apiKey: String): News






    companion object {
        val baseUrl = BuildConfig.API_BASE_URL
        var app : Application = Swivel.applicationContext() as Application
        fun create(): APIInterface = create(baseUrl.toHttpUrlOrNull()!!)

        var cacheSize = 10 * 1024 * 1024
        var httpCacheDirectory = File(app.cacheDir, "responses")
        var cache = Cache(httpCacheDirectory, cacheSize.toLong())

        fun create(httpUrl: HttpUrl): APIInterface {

            var client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .cache(cache).addNetworkInterceptor { chain ->
                    val response = chain.proceed(chain.request())
                    val maxAge = 0
                    response.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .removeHeader("Pragma")
                        .build()
                }.addInterceptor { chain ->
                    var request = chain.request()
                    if (!InternetConnection.checkInternetConnection()) {
                        val maxStale = 60 * 60 * 24 * 30
                        request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                            .removeHeader("Pragma")
                            .build()
                    }
                    chain.proceed(request)
                }.build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(APIInterface::class.java)
        }
    }
}