package com.wrapx.weatherapp.data.repo


import com.wrapx.weatherapp.data.Result
import com.wrapx.weatherapp.data.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository: Repository {

   override suspend fun fetchCurrentWeather() = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = ApiClient.API.fetchCurrentWeather("mumbai")
            Result.Success(result.body()!!)
        }catch (throwable: Exception) {
            Result.Error(throwable)
        }
    }

}