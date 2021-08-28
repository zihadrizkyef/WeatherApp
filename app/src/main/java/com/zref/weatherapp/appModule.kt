package com.zref.weatherapp

import com.zref.repository.IWeatherRepository
import com.zref.repository.WeatherRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IWeatherRepository> { WeatherRepository() }
    viewModel { MainViewModel(get()) }
}