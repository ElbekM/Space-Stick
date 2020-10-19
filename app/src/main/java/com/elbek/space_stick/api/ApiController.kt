package com.elbek.space_stick.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiController {
    @GET("api/check")
    suspend fun checkConnection()

    @POST("/api/switch/mode")
    suspend fun switchMode(
        @Query("ssid") ssid: String,
        @Query("password") password: String
    )

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

    @POST("api/mode/pattern/color")
    suspend fun setPatternColor(
        @Query("r") r: Int,
        @Query("g") g: Int,
        @Query("b") b: Int
    )
}
