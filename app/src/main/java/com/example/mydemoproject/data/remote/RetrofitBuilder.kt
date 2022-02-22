package com.example.mydemoproject.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    val intercep = Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", "26763d7bf2e94098192e629eb975dab0")
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .method(request.method, request.body)
            .build()
        chain.proceed(newRequest)
    }
    val okhttp = OkHttpClient.Builder()
        .apply {
            addInterceptor(intercep)
        }
        .build()

    fun getRetrofitApp(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp)
        .build()

    var apiService: ApiService = getRetrofitApp().create(ApiService::class.java)
}