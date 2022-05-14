package com.example.weather.viewholder

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.Viewmodel.My_weather_model

class Addcity_viewholder (itemView: View,var model: My_weather_model) : RecyclerView.ViewHolder(itemView) {
var add: ImageView? =itemView.findViewById(R.id.add)as ImageView?

}