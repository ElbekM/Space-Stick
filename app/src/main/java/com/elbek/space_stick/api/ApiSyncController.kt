package com.elbek.space_stick.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiSyncController {
    @GET
    suspend fun checkConnection(@Url url: String)

    @POST
    suspend fun switchMode(@Url url: String)

    @POST
    suspend fun setPattern(@Url url: String)

    @POST
    suspend fun setSpeed(@Url url: String)

    @POST
    suspend fun setBrightness(@Url url: String)

    @POST
    suspend fun setPatternColor(@Url url: String)
}
