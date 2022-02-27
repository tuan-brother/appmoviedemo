package com.example.mydemoproject.data.repository

import com.example.mydemoproject.data.model.DaTaResponse
import com.example.mydemoproject.data.remote.ApiHelper

class MovieRepository(private val apiHelper: ApiHelper) {
    suspend fun getMovies(page: Int): DaTaResponse? =
        apiHelper.getDataMovies(pageCount = page)
}