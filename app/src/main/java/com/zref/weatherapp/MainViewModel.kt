package com.zref.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zref.model.WeatherByCity
import com.zref.repository.IWeatherRepository

class MainViewModel(
    private val repository: IWeatherRepository
) : ViewModel() {
    val weather = MutableLiveData<WeatherByCity>()
    val errorMessage = MutableLiveData<String>()
    val isLoadingData = MutableLiveData<Boolean>()

    fun getWeather(city: String) {
        isLoadingData.value = true
        repository.getWeatherByCity(
            city,
            {
                isLoadingData.value = false
                weather.value = it
            },
            {
                isLoadingData.value = false
                errorMessage.value = it.message
            }
        )
    }
}