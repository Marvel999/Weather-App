package com.wrapx.weatherapp.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.wrapx.weatherapp.R
import com.wrapx.weatherapp.data.model.Location
import com.wrapx.weatherapp.extention.load
import com.wrapx.weatherapp.util.LocationUtils
import com.wrapx.weatherapp.util.PermissionUtils
import com.wrapx.weatherapp.util.Util
import kotlinx.android.synthetic.main.error_layout.*


class WeatherScreenFragment : Fragment() {

    private lateinit var viewModel: WeatherScreenViewModel
    private lateinit var weatherLayout: ViewGroup
    private lateinit var errorLayout: ViewGroup
    private lateinit var refresh: Button
    private lateinit var temperatureTv: TextView
    private lateinit var locationTxv: TextView
    private lateinit var weaterIcon: ImageView
    private val MYTAG = "WeatherScreenFragment"

    private var userLocation: String = ""

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var currentLocation = MutableLiveData<Location>()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressDialog = ProgressDialog(requireContext())
        weatherLayout = view.findViewById(R.id.weather_layout)
        errorLayout = view.findViewById(R.id.error_layout)
        refresh = errorLayout.findViewById(R.id.refrace_btn)
        temperatureTv = weatherLayout.findViewById(R.id.temperature_txv)
        locationTxv = weatherLayout.findViewById(R.id.location_txv)
        weaterIcon = weatherLayout.findViewById(R.id.weather_Img)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        progressDialog.setMessage("Fetching location and data please wait...")
        // checkLocationStatus()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherScreenViewModel::class.java)


        viewModel.networkError.observe(viewLifecycleOwner, {

            //dismiss dialog
            progressDialog.dismiss()
            Log.e("MY TAG", "ERROR $it")

            Toast.makeText(requireContext(), "Internal server error!!", Toast.LENGTH_SHORT).show()
            errorLayout.visibility = View.VISIBLE
            weatherLayout.visibility = View.GONE
            animationView.setAnimation(R.raw.retry)
        })

        viewModel.currentWeather.observe(viewLifecycleOwner, {

            //dismiss dialog
            progressDialog.dismiss()

            errorLayout.visibility = View.GONE
            weatherLayout.visibility = View.VISIBLE
            setDataOnView(it.current.temperature)

        })

        refresh.setOnClickListener {
            callApi(userLocation)
        }

        currentLocation.observe(viewLifecycleOwner, {
            if (it.lat == null || it.lng == null) return@observe

           this.userLocation = LocationUtils.getUserLocation(it.lat, it.lng, requireContext())
            locationTxv.setText(userLocation)
            callApi(userLocation)
        })

    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            currentLocation.value = Location(
                mLastLocation.latitude,
                mLastLocation.longitude
            )
        }
    }

    private fun setDataOnView(temperature: Int) {
        val temp = "${temperature.toString().trim()} °C"
        temperatureTv.text = temp
        if (temperature <= 30) {
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_cloud)?.let {
                weaterIcon.load(it)
            }
        } else {
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_sun)?.let {
                weaterIcon.load(it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        //Show progress dialog
        progressDialog.show()
        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient.lastLocation
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun checkLocationEnabled() {
        when {
            PermissionUtils.isLocationEnabled(requireContext()) -> {
                requestNewLocationData()
            }
            else -> {
                PermissionUtils.showGPSNotEnabledDialog(requireContext())
            }
        }
    }


    private fun callApi(location: String) {
        if (Util.isNetworkAvailable(requireContext())) {
            viewModel.getCurrentWeather(location)
        } else {
            progressDialog.dismiss()
            Toast.makeText(requireContext(), "Internet is not connected!!", Toast.LENGTH_SHORT).show()
            errorLayout.visibility = View.VISIBLE
            weatherLayout.visibility = View.GONE
            animationView.setAnimation(R.raw.no_internet)
        }
    }


    private fun checkPermissions() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED) {
            //Permission not granted request for new permission
            requestPermissions.launch(
                ACCESS_FINE_LOCATION
            )
        } else {
            //Permission granted
            checkLocationEnabled()
        }
    }

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissions ->

            if (permissions == true) {
                checkLocationEnabled()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission Needed")
                    .setCancelable(false)
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        val showRationale =
                            shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)
                        //Prompt the user once explanation has been shown
                        if (!showRationale) {
                            openSomeActivityForResult()
                        } else {
                            checkPermissions()
                        }
                    }
                    .create()
                    .show()
            }
        }
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            checkPermissions()
        }

    private fun openSomeActivityForResult() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        resultLauncher.launch(intent)
    }

}