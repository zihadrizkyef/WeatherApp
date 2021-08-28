package com.zref.repository

import com.zref.model.WeatherByCity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository : IWeatherRepository {
    override fun getWeatherByCity(
        cityName: String,
        onFetched: (response: WeatherByCity) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        Retrofit.apiClient.getToday(cityName)
            .enqueue(object : Callback<WeatherByCityResponse> {
                override fun onResponse(
                    call: Call<WeatherByCityResponse>,
                    response: Response<WeatherByCityResponse>
                ) {
                    if (response.body() != null) {
                        onFetched(response.body()!!.toEntity())
                    } else {
                        onError(Throwable())
                    }
                }

                override fun onFailure(call: Call<WeatherByCityResponse>, t: Throwable) {
                    onError(t)
                }
            })
    }

    fun WeatherByCityResponse.toEntity(): WeatherByCity {
        return WeatherByCity(
            this.name?:"",
            this.weather?.get(0)?.main?:"",
            this.weather?.get(0)?.description?:"",
            this.weather?.get(0)?.icon?:"",
            this.main?.pressure?:0.0,
            this.main?.humidity?:0.0,
            this.main?.temp?:0.0
        )
    }
}