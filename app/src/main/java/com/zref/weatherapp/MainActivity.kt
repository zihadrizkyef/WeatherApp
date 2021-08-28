package com.zref.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.ListPopupWindow
import com.bumptech.glide.Glide
import com.zref.weatherapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getWeather("Gdansk")
        setupObserver()
        setupView()
    }

    private fun setupObserver() {
        val date = Date()
        val sdf = SimpleDateFormat("EEEE, MMMMM d", Locale.US)
        viewModel.weather.observe(this, {
            binding.textMainInfo.text = sdf.format(date) + "\n" + it.city
            binding.textHumidity.text = "${it.humidity} hPa.\n ${it.pressure}%"
            binding.textDegrees.text = it.temperature.toString()
            binding.textWeather.text = it.weatherTitle
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/${it.iconCode}@2x.png")
                .into(binding.iconWeather)
        })
    }

    private fun setupView() {
        spinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_dropdown, R.id.textCityName)
        spinnerAdapter.addAll(
            "Gdansk",
            "Warszawa",
            "Krakow",
            "Wroclaw",
            "Lodz"
        )

        binding.spinnerCity.setOnClickListener {
            openCityDropdown()
        }
    }

    private fun openCityDropdown() {
        val dropDown = ListPopupWindow(this)
        dropDown.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.bg_transparent
            )
        )
        dropDown.anchorView = binding.spinnerCity
        dropDown.setAdapter(spinnerAdapter)
        dropDown.setOnItemClickListener { _, _, position, _ ->
            val selectedCity = spinnerAdapter.getItem(position)!!
            viewModel.getWeather(selectedCity)
            dropDown.dismiss()
        }
        dropDown.show()
    }
}