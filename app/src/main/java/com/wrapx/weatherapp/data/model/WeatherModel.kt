package com.wrapx.weatherapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherModel(
   @field:Json(name = "current")
   val current: Current
)

@JsonClass(generateAdapter = true)
data class Current(
    @field:Json(name = "temperature")
    val temperature: Int
)
