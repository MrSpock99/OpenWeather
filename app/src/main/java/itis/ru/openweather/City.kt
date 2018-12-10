package itis.ru.openweather

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
data class City(
        @SerializedName("id")
        @PrimaryKey
        var id: Int? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("sys")
        @Embedded(prefix = "sys")
        var sys: Sys? = null,

        @SerializedName("main")
        @Embedded(prefix = "main")
        var main: WeatherTemp? = null,

        @SerializedName("wind")
        @Embedded(prefix = "wind")
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
