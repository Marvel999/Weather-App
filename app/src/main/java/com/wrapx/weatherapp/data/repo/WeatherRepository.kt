package com.wrapx.weatherapp.data.repo


import com.wrapx.weatherapp.data.Result
import com.wrapx.weatherapp.data.api.ApiClient

class WeatherRepository : Repository {

    override suspend fun fetchCurrentWeather(location: String) = run {
        return@run try {
            val result = ApiClient.API.fetchCurrentWeather(location)
            Result.Success(result.body()!!)
        } catch (throwable: Exception) {
            Result.Error(throwable)
        }
    }
}