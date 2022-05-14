package com.example.weather.Bean

import androidx.room.*
import androidx.room.Entity
import java.util.ArrayList

@Entity(tableName = "citylist")
class City {
    @PrimaryKey
    lateinit var cityid:String
    @ColumnInfo(name = "name")
    var cityname:String?=null
    @ColumnInfo(name = "country")
    var country:String?=null
   @Ignore
    var weather: ArrayList<Weather> = ArrayList<Weather>()
}