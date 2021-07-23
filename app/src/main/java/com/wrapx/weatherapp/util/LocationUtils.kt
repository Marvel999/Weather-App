package com.wrapx.weatherapp.util

import android.content.Context
import android.location.Geocoder
import android.util.Log
import java.util.*

object LocationUtils {
    fun getUserLocation(lat: Double,long: Double, context: Context):String{
        var cityName:String = ""
        var countryName = ""
        val geoCoder = Geocoder(context, Locale.getDefault())
        val Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        val location = "$cityName, $countryName"
        Log.e(
            "Debug:",
            "Your City: " + cityName + " ; your Country " + countryName + " Address " + Adress.get(0).postalCode
        )
        return location
    }
}


