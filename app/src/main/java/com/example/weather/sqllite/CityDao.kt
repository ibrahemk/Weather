package com.example.weather.sqllite

import androidx.room.*
import com.example.weather.Bean.City

@Dao
interface CityDao {
    @get:Query("SELECT * FROM citylist")
    val getallcity: List<City>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(games: List<City?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(vararg city: City)

    @Delete
    fun deleteCity(vararg city: City)

    @Query("DELETE FROM citylist")
    fun deletecities()
}