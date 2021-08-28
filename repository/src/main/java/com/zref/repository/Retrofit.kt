package com.zref.repository

import okhttp3.OkHttpClient
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory


object Retrofit {
    val apiClient: ApiInterface

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()

        val retrofit = retrofit2.Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiClient = retrofit.create(ApiInterface::class.java)
    }

    private class ApiKeyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", BuildConfig.API_KEY)
                .build()
            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }
}