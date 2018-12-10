package itis.ru.openweather

import com.google.gson.annotations.SerializedName

data class CitiesForecast(
    @SerializedName("list")
    internal var list: List<City>
)
