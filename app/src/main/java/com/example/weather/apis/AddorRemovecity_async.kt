package com.example.weather.apis

import com.example.weather.Bean.City
import com.example.weather.Coroutine.AsyncRq
import com.example.weather.Viewmodel.My_weather_model
import com.example.weather.sqllite.AppDatabase

class AddorRemovecity_async(var model:My_weather_model,var boolean: Boolean,var postion:Int,var city: City): AsyncRq<String, Any>() {
    override fun onPreExecute() {
        super.onPreExecute()
        model.showloading(true)
    }
    override fun doInBackground(vararg Param: Any): String {

        if (boolean) {
            AppDatabase.getAppDatabase(model.activity)!!.CityDao()!!.insertCity(city)
            AppDatabase.destroyInstance()
        }else{
            AppDatabase.getAppDatabase(model.activity)!!.CityDao()!!.deleteCity(city)
            AppDatabase.destroyInstance()
        }
        return ""

    }

    override fun onPostExecute(t: String) {
        super.onPostExecute(t)

model.handleaddorremove(city,boolean,postion)


        model.showloading(false)

    }
}