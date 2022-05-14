package com.example.weather.apis

import com.example.weather.Coroutine.AsyncRq
import com.example.weather.Helper.Globels
import com.example.weather.Requests
import com.example.weather.Viewmodel.My_weather_model

class Getcitybylocation_async(var model:My_weather_model):AsyncRq<String,Any>() {

    override fun onPreExecute() {
        super.onPreExecute()
        model.showloading(true)
        model.getloaction()
    }

    override fun doInBackground(vararg Param: Any): String {
        val url: String =
            Globels().getweatherbycityid + "lat=${model.location!!.latitude}&lon=${model.location!!.longitude}&units=${model.temptype}${Globels().APIkey}"
       val output = Requests().sendGet(url)
        return output
    }

    override fun onPostExecute(t: String) {
        super.onPostExecute(t)
        if (t.trim().isNotEmpty()){
model.showdata(model.parsejosn(t))

        }else {
            model.showloading(false)
        }
    }

}