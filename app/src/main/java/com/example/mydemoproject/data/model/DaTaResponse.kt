package com.example.mydemoproject.data.model

import com.squareup.moshi.Json

open class DaTaResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<Movies>? = null,
    @Json(name = "total_pages") val total_pages: Double?,
    @Json(name = "total_results") val total_results: Double?
)