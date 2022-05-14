package com.example.weather.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Bean.City
import com.example.weather.Helper.Tools
import com.example.weather.R
import com.example.weather.Viewmodel.My_weather_model
import com.example.weather.apis.AddorRemovecity_async
import java.text.FieldPosition

class Cities_viewholder(itemView: View,var model:My_weather_model) : RecyclerView.ViewHolder(itemView) {
    var delete: ImageView? =itemView.findViewById(R.id.delete)as ImageView?
    var countryname: TextView? =itemView.findViewById(R.id.countryname)as TextView?
    var cityname: TextView? =itemView.findViewById(R.id.cityname)as TextView?
    var main: ConstraintLayout? =itemView.findViewById(R.id.main)as ConstraintLayout?
    fun handleui(city:City,position: Int){
      countryname!!.text = city.country
      cityname!!.text = city.cityname
        if (position==0){
         delete!!.visibility=View.GONE
        }else {
            delete!!.visibility=View.VISIBLE
            delete!!.setOnClickListener(deletebutton(city, position))
        }
    main!!.setOnClickListener(showweather(city))
    }

     fun deletebutton(city: City, postion: Int): View.OnClickListener {
        return View.OnClickListener {
            Tools.async(AddorRemovecity_async(model,false,postion,city))
        }
    }
 fun showweather(city: City): View.OnClickListener {
        return View.OnClickListener {
            model.showcity_weather(city)
        }
    }



}