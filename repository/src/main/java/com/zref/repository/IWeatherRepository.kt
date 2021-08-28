package com.zref.repository

import com.zref.model.WeatherByCity

interface IWeatherRepository {
    fun getWeatherByCity(
        cityName: String,
        onFetched: (response: WeatherByCity) -> Unit,
        onError: (throwable: Throwable) -> Unit
    )
}