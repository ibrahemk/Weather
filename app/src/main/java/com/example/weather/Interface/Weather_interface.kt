package com.example.weather.Interface

import android.app.Dialog
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.example.weather.Bean.City
import com.example.weather.Bean.Weather
import com.example.weather.Fragment.My_weather_Fragment
import java.text.FieldPosition

interface Weather_interface {
    fun resumecallapi(activity: FragmentActivity, fclass: My_weather_Fragment)
    fun callapi()
    fun getallapis()
    fun cancelapi()
    fun showloading(show:Boolean)
    fun handleui()
    fun getloaction()
    fun showdata(city: City)
    fun addadapter()
    fun parsejosn(output:String): City
    fun changetype(): View.OnClickListener
    fun handlecitiesui(cities:ArrayList<City>)

    fun showadd_popup(): View.OnClickListener
    fun addcityname(cityname: EditText?, addpop: Dialog): View.OnClickListener
    fun addcitydata(city: City)
    fun handleaddorremove(city: City,boolean: Boolean,position: Int)

}