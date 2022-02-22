package com.example.mydemoproject.data.remote

import com.example.mydemoproject.data.model.DaTaResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(ApiPaths.DISCOVER_MOVIE)
    suspend fun getUsers(): Response<DaTaResponse>
}