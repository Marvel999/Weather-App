package com.wrapx.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.wrapx.weatherapp.ui.WeatherScreenViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}