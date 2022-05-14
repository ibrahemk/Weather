package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Bean.Weather
import com.example.weather.R
import com.example.weather.Viewmodel.My_weather_model
import com.example.weather.viewholder.Weather_viewholder

class Weather_adapter(var model: My_weather_model) : RecyclerView.Adapter<Weather_viewholder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Weather_viewholder {
        return Weather_viewholder(LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false),model)
    }

    override fun onBindViewHolder(holder: Weather_viewholder, position: Int) {
        var weather: Weather = model.city!!.weather[position]
        holder.handleui(weather)
    }

    override fun getItemCount(): Int {
        if (model.city!=null){
            return model.city!!.weather.size
        }
        return 0
    }
}