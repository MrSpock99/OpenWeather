package itis.ru.openweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("find")
    fun getCityList(@Query("lat") lat: Double?,
                    @Query("lon") lon: Double?,
                    @Query("cnt") cnt: Int = 20,
                    @Query("appid") appid: String = "56fc6c6cb76c0864b4cd055080568268"): Call<CitiesForecast>
}
