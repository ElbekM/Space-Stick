package com.elbek.space_stick.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiController {
    @GET("api/check")
    suspend fun checkConnection()

    @POST("api/mode")
    suspend fun setPattern(
        @Query("pattern") pattern: Int
    )

    @POST("api/mode/speed")
    suspend fun setSpeed(
        @Query("s") speed: Int
    )

    @POST("api/mode/brightness")
    suspend fun setBrightness(
        @Query("b") brightness: Int
    )
}
