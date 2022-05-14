package com.example.weather.apis

import com.example.weather.Coroutine.AsyncRq
import com.example.weather.Helper.Globels
import com.example.weather.Requests
import com.example.weather.Viewmodel.My_weather_model

class Getweatherbycity(var model: My_weather_model,var city:String?,var type:Int) : AsyncRq<String, Any>() {


    override fun onPreExecute() {
        super.onPreExecute()
        model.showloading(true)
    }
    override fun doInBackground(vararg Param: Any): String {
        //        api.openweathermap.org/data/2.5/forecast?id={city ID}&appid={API key}
        var output:String=""
            if (type==1) {
                var url: String =
                    "${Globels().getweatherbycityid} id=${city}&units=${model.temptype}${Globels().APIkey}"
                output = Requests().sendGet(url)
            }else{
                var url: String =
                    "${Globels().getweatherbycityid}q=$city&units=${model.temptype}${Globels().APIkey}"
                output = Requests().sendGet(url)
            }

        return output
    }

    override fun onPostExecute(t: String) {
        super.onPostExecute(t)
       model.addcitydata(model.parsejosn(t))




        model.showloading(false)
    }
}