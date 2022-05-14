package com.example.weather.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
//import com.appital.classfit.viewmodel_factory.Viewmodel_factory
import com.example.weather.R
import com.example.weather.Viewmodel.My_weather_model


class My_weather_Fragment : Fragment() {
    lateinit var weathers_list: RecyclerView
    lateinit var cities_list: RecyclerView
    lateinit var cityname: TextView
    lateinit var C_or_f: Button
    lateinit var load: LinearLayout


     val model: My_weather_model by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weathers_list = view.findViewById<View>(R.id.weather_list) as RecyclerView
        cities_list = view.findViewById<View>(R.id.cities_list) as RecyclerView
        cityname = view.findViewById<View>(R.id.city_name) as TextView
        load = view.findViewById<View>(R.id.load) as LinearLayout
        C_or_f = view.findViewById<View>(R.id.C_or_f) as Button
    }


    override fun onResume() {
        super.onResume()
        model.resumecallapi(requireActivity(),this@My_weather_Fragment)
    }

    override fun onDetach() {
        super.onDetach()
        model.cancelapi()
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            My_weather_Fragment().apply {

            }
    }
}