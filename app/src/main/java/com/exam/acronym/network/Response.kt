package com.exam.acronym.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network response class
 */
class Response {

    @JsonClass(generateAdapter = true)
    data class AcronymData (
        @Json(name = "sf") val sf: String,
        @Json(name = "lfs") val lfs: List<LfsItem>
    )

    @JsonClass(generateAdapter = true)
    data class LfsItem(
        @Json(name = "lf") val lf: String,
        @Json(name = "freq") val freq: Int,
        @Json(name = "since") val since: Int,
        @Json(name = "vars") val vars: List<VarItem>,
        var showVars: Boolean = false,
    )

    @JsonClass(generateAdapter = true)
    class VarItem(
        @Json(name = "lf") val lf: String,
        @Json(name = "freq") val freq: Int,
        @Json(name = "since") val since: Int,
    )

}