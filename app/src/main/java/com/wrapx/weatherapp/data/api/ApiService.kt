package com.wrapx.weatherapp.data.api

import com.wrapx.weatherapp.data.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/current")
    suspend fun fetchCurrentWeather(
        @Query("query") cityName:String,
    ): Response<WeatherModel>
}