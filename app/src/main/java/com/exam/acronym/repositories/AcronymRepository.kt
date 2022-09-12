package com.exam.acronym.repositories

import com.exam.acronym.entities.DataResult
import com.exam.acronym.network.AcronymAPI
import com.exam.acronym.network.Response

/**
 * Repository class
 */
class AcronymRepository(private val api: AcronymAPI) {

    suspend fun getMeanings(sf: String): DataResult<List<Response.LfsItem>> {
        return try {
            val result = api.getMeanings(sf)
            if (result.isEmpty()) {
                val e = Exception("Empty response")
                DataResult.Failure(e)
            } else {
                DataResult.Success(result.first().lfs)
            }
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }

}