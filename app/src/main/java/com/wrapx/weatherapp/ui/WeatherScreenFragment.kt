package com.wrapx.weatherapp.ui

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.wrapx.weatherapp.R
import com.wrapx.weatherapp.extention.load
import com.wrapx.weatherapp.util.Util
import kotlinx.android.synthetic.main.error_layout.*

class WeatherScreenFragment : Fragment() {

    private lateinit var viewModel: WeatherScreenViewModel
    private lateinit var weatherLayout:ViewGroup;
    private lateinit var errorLayout:ViewGroup;
    private lateinit var refresh:Button;
    private lateinit var temperatureTv:TextView;
    private lateinit var locationTxv:TextView;
    private lateinit var weaterIcon:ImageView;
    private val MYTAG="WeatherScreenFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        weatherLayout=view.findViewById(R.id.weather_layout);
        errorLayout=view.findViewById(R.id.error_layout);
        refresh=errorLayout.findViewById(R.id.refrace_btn)
        temperatureTv=weatherLayout.findViewById(R.id.temperature_txv)
        locationTxv=weatherLayout.findViewById(R.id.location_txv)
        weaterIcon=weatherLayout.findViewById(R.id.weather_Img)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherScreenViewModel::class.java)


        viewModel.networkError.observe(viewLifecycleOwner, {
            Log.e(MYTAG, "Error: $it")
            errorLayout.visibility=View.VISIBLE
            weatherLayout.visibility=View.GONE
            animationView.setAnimation(R.raw.retry)
        })

        viewModel.currentWeather.observe(viewLifecycleOwner, {
            errorLayout.visibility=View.GONE
            weatherLayout.visibility=View.VISIBLE
           setDataOnView(it.current.temperature)
            Log.e(MYTAG, "Success: $it")

        })
        callApi()
        refresh.setOnClickListener {
            callApi()
        }

    }

    private fun setDataOnView(temperature: Int){
        temperatureTv.setText(temperature.toString().trim()+"°C")
        if (temperature<=30){
            weaterIcon.load(resources.getDrawable(R.drawable.ic_cloud))
        }else{
            weaterIcon.load(resources.getDrawable(R.drawable.ic_sun))

        }
    }


    private fun callApi(){
        @RequiresApi(Build.VERSION_CODES.M)
        if (Util.isOnline(this.requireContext())){
            viewModel.getCurrentWeather()
        }else{
            errorLayout.visibility=View.VISIBLE
            weatherLayout.visibility=View.GONE
            animationView.setAnimation(R.raw.no_internet)
        }
    }

}