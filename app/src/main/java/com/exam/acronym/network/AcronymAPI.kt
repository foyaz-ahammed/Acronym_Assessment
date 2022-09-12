package com.exam.acronym.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface for the network call
 */
interface AcronymAPI {
    @GET("dictionary.py")
    suspend fun getMeanings(@Query("sf") sf: String): List<Response.AcronymData>
}