package itis.ru.openweather

import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("sys")
        var sys: Sys? = null,
        @SerializedName("main")
        var main: WeatherTemp? = null,
        @SerializedName("wind")
        var wind: Wind? = null
)

data class Sys(var country: String? = null)

data class WeatherTemp(
        var temp: Double? = null,
        var pressure: Double? = null,
        var humidity: Double? = null
)

data class Wind(
        var speed: Double? = null,
        var deg: Double? = null,
        var gust: Double? = null
)