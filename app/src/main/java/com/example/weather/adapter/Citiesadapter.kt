package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Bean.City
import com.example.weather.R
import com.example.weather.Viewmodel.My_weather_model
import com.example.weather.viewholder.Addcity_viewholder
import com.example.weather.viewholder.Cities_viewholder

class Citiesadapter (var model:My_weather_model) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            return Cities_viewholder(LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false),model =model )
        }else{
            return Addcity_viewholder(LayoutInflater.from(parent.context).inflate(R.layout.addcity, parent, false),model =model)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (holder is Cities_viewholder){
           var city: City = model.CitiesList[position]
           holder.handleui(city,position)
       }else if (holder is Addcity_viewholder){
           holder.add!!.setOnClickListener(model.showadd_popup())
       }
    }

    override fun getItemCount(): Int {
        return if (model.CitiesList.size in 1..4) {
            model.CitiesList.size+1
        }else{
            model.CitiesList.size
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (model.CitiesList.size<5&&position>model.CitiesList.size-1) {
            return 0
        }else{
            return 1
        }
    }
}