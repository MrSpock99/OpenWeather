package itis.ru.openweather.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import itis.ru.openweather.City

private const val DB_NAME: String = "OPEN_WEATHER.db"

@Database(entities = [City::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
