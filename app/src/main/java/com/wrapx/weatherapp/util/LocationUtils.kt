package com.wrapx.weatherapp.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

object LocationUtils {
    const val MY_PERMISSIONS_REQUEST_LOCATION = 99
}

fun Fragment.checkLocationPermission(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requireActivity().checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestLocationPermission()
//        // Should we show an explanation?
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                requireActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        ) {
//            // Show an explanation to the user *asynchronously* -- don't block
//            // this thread waiting for the user's response! After the user
//            // sees the explanation, try again to request the permission.
//            AlertDialog.Builder(requireContext())
//                .setTitle("Location Permission Needed")
//                .setMessage("This app needs the Location permission, please accept to use location functionality")
//                .setPositiveButton(
//                    "OK"
//                ) { _, _ ->
//                    //Prompt the user once explanation has been shown
//                    requestLocationPermission()
//                }
//                .create()
//                .show()
//        } else {
//
//            // No explanation needed, we can request the permission.
//            requestLocationPermission()
//        }
        return false
    }
    return true
}

fun Fragment.requestLocationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
            LocationUtils.MY_PERMISSIONS_REQUEST_LOCATION
        )
    } else {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LocationUtils.MY_PERMISSIONS_REQUEST_LOCATION
        )
    }
}

