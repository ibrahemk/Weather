package com.appital.classfit.viewmodel_factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.Fragment.My_weather_Fragment
import com.example.weather.Viewmodel.My_weather_model


//class Viewmodel_factory(var activity: FragmentActivity): ViewModelProvider.Factory {
//    lateinit var fclass:Fragment
////    constructor(activity: FragmentActivity,  fclass:Fragment) : this(activity){
////        this.fclass=fclass
////    }
////    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
////        when {
////            modelClass.isAssignableFrom(My_weather_model::class.java) -> {
////                return My_weather_model(activity,fclass = fclass as My_weather_Fragment) as T
////            }
////            else -> throw IllegalArgumentException("Unknown View Model Class")
////        }
////    }
//}