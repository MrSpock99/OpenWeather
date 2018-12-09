package itis.ru.openweather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itis.ru.openweather.City
import itis.ru.openweather.R
import kotlinx.android.synthetic.main.fragment_weather_city.view.*


class WeatherCityFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_weather_city, container, false)
        rootView.tv_cityName.text = getString(R.string.tv_cityName_text, city?.name)
        rootView.tv_humidity.text = getString(R.string.tv_humidity_text, city?.main?.humidity.toString())
        rootView.tv_press.text = getString(R.string.tv_press_text, city?.main?.pressure.toString())
        rootView.tv_temperature.text = getString(R.string.tv_temp_text, city?.main?.temp.toString())
        rootView.tv_wind.text = getString(R.string.tv_wind_text, city?.wind?.deg.toString())
        return rootView
    }

    companion object {
        var city: City? = null
        fun newInstance() = WeatherCityFragment()
    }
}
