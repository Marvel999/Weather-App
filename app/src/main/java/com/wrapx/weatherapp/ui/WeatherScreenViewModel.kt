package com.wrapx.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrapx.weatherapp.data.Result
import com.wrapx.weatherapp.data.repo.Repository
import com.wrapx.weatherapp.data.repo.WeatherRepository
import com.wrapx.weatherapp.data.model.WeatherModel
import kotlinx.coroutines.launch

class WeatherScreenViewModel : ViewModel() {

    private val repository: Repository = WeatherRepository()
    private val _currentWeather = MutableLiveData<WeatherModel>()
    val currentWeather: LiveData<WeatherModel> = _currentWeather

    private val _networkError = MutableLiveData<String>()
    val networkError: LiveData<String> = _networkError

    fun getCurrentWeather() {
        viewModelScope.launch {
            when (val result = repository.fetchCurrentWeather()) {
                is Result.Success -> _currentWeather.postValue(result.data)
                is Result.Error -> _networkError.postValue("Something went wrong!! ${result.exception}")
            }
        }
    }
}