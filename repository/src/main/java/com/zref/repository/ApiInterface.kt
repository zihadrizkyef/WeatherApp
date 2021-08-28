package com.zref.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun getToday(
        @Query("q") cityName: String
    ): Call<WeatherByCityResponse>
}