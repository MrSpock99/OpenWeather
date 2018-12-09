package itis.ru.openweather.database

import android.arch.persistence.room.*
import io.reactivex.Flowable
import itis.ru.openweather.City

@Dao
interface WeatherDataDao {
    @Query("SELECT * FROM city")
    fun getAll(): Flowable<List<City>>

    @Query("SELECT * FROM city WHERE id = :id")
    fun getById(id: Int): City

    @Query("DELETE FROM city")
    fun nukeTable()

    @Insert
    fun insert(city: City)

    @Update
    fun update(city: City)

    @Delete
    fun delete(city: City)
}
