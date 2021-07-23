package com.wrapx.weatherapp.util

import android.content.Context
import android.location.Geocoder
import java.util.*

object LocationUtils {
    fun getUserLocation(lat: Double, long: Double, context: Context): String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 3)
        val cityName = address[0].locality
        val countryName = address[0].countryName
        return "$cityName, $countryName"
    }
}


