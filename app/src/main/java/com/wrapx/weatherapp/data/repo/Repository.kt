package com.wrapx.weatherapp.data.repo

import com.wrapx.weatherapp.data.Result
import com.wrapx.weatherapp.data.model.WeatherModel

interface Repository {
    suspend fun fetchCurrentWeather(location: String): Result<WeatherModel>
}