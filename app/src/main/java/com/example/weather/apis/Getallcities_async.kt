package com.example.weather.apis

import androidx.fragment.app.FragmentActivity
import com.example.weather.Bean.City
import com.example.weather.Coroutine.AsyncRq
import com.example.weather.Helper.Tools
import com.example.weather.Viewmodel.My_weather_model
import com.example.weather.sqllite.AppDatabase
import java.util.*

class Getallcities_async(var model:My_weather_model) : AsyncRq<String, Any>()  {

    var CitiesList: ArrayList<City> =
        ArrayList<City>()



    override fun doInBackground(vararg Param: Any): String {

        try {
        CitiesList.addAll(AppDatabase.getAppDatabase(model.activity)!!.CityDao()!!.getallcity!!)
        if (CitiesList.size>0){
            return "full"
        }else{
            return "error"
        }
        }catch (e:Exception){
            return "serror"
        }
    }

    override fun onPostExecute(t: String) {

            model.handlecitiesui(CitiesList)

        model.showloading(false)

    }

}