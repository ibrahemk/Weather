package com.example.weather.viewholder

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.weather.Bean.Weather
import com.example.weather.R
import com.example.weather.Viewmodel.My_weather_model
import java.text.SimpleDateFormat
import java.util.*

class Weather_viewholder (itemView: View,var model:My_weather_model) : RecyclerView.ViewHolder(itemView) {
    var icon: ImageView? =itemView.findViewById(R.id.icon)as ImageView?
    var date: TextView? =itemView.findViewById(R.id.date)as TextView?
    var mainwea: TextView? =itemView.findViewById(R.id.mainwea)as TextView?
    var des: TextView? =itemView.findViewById(R.id.des)as TextView?
    var temp: TextView? =itemView.findViewById(R.id.temp)as TextView?
    var maxmintemp: TextView? =itemView.findViewById(R.id.maxmintemp)as TextView?


    fun bindPhoto(
        photoUrl: String?) {
        var url:String= "http://openweathermap.org/img/w/$photoUrl.png"

        Glide.with(model.activity)
            .asBitmap()
            .load(url)
            .into(icon!!)
    }

    fun handleui(weather: Weather){
      mainwea!!.text=weather.mainwea
        des!!.text=weather.description

        if (model.temptype == "metric") {
            temp!!.text = "${weather.temp} C"
            maxmintemp!!.text = "${weather.temp_min}/${weather.temp_max} C"
        }else{
            temp!!.text = "${weather.temp} F"
            maxmintemp!!.text = "${weather.temp_min}/${weather.temp_max} F"
        }

        date!!.text= SimpleDateFormat("dd/MM/yyyy hh:mm a ").format(Date(weather.date!!.toLong()*1000))
        bindPhoto(weather.icon)
    }
}