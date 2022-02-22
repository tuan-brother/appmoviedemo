package com.example.mydemoproject.data.remote

import android.util.Log
import com.example.mydemoproject.data.model.DaTaResponse

class ApiHelper(private val apiService: ApiService) {

    suspend fun getDataMovies(): DaTaResponse? {
        return apiService.getUsers().body()
    }

}