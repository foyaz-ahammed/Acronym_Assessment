package com.exam.acronym.entities

import java.lang.Exception

/**
 * Response class showing [Success] or [Failure]
 */
sealed class DataResult<T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Failure<T>(val exception: Exception): DataResult<T>()
}
