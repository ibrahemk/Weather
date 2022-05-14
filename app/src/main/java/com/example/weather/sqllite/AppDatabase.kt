package com.example.weather.sqllite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.Bean.City


@Database(entities = [City::class], version = 13)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CityDao(): CityDao?

    companion object {
        private var mInstance: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase? {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "Cityweather_data"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return mInstance
        }

        fun destroyInstance() {
            if (mInstance != null) {
                mInstance!!.close()
            }
            mInstance = null
        }
    }
}
