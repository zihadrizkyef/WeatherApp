package com.zref.model

data class WeatherByCity(
    val city: String,
    val weatherTitle: String,
    val weatherDesc: String,
    val iconCode: String,
    val pressure: Double,
    val humidity: Double,
    val temperature: Double
)
