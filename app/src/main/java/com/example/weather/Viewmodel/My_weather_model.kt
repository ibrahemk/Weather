package com.example.weather.Viewmodel

import android.app.Dialog
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.hilt.Assisted
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Bean.City
import com.example.weather.Bean.Weather
import com.example.weather.Coroutine.AsyncRq
import com.example.weather.Fragment.My_weather_Fragment
import com.example.weather.Helper.Tools
import com.example.weather.Interface.Weather_interface
import com.example.weather.R
import com.example.weather.adapter.Citiesadapter
import com.example.weather.adapter.Weather_adapter
import com.example.weather.apis.AddorRemovecity_async
import com.example.weather.apis.Getallcities_async
import com.example.weather.apis.Getcitybylocation_async
import com.example.weather.apis.Getweatherbycity
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import org.json.JSONObject
@HiltViewModel
class My_weather_model():ViewModel(), LifecycleObserver, Weather_interface,
    LocationListener {
 var async:AsyncRq<String,Any>?=null
 var citiesasync:AsyncRq<String,Any>?=null
    var CitiesList: ArrayList<City> = ArrayList<City>()
    var location: Location?=null;
    var city: City?=null;
    var temptype="metric"
    var Current:Boolean=true
    lateinit var activity:FragmentActivity
    lateinit var fclass:My_weather_Fragment
    override fun resumecallapi(activity:FragmentActivity,fclass:My_weather_Fragment) {
      this.activity=activity
        this.fclass=fclass

        Current=true
handleui()
        if (Tools.isLocationPermissionGranted(activity)&&Tools.checklocation(activity))     {
            callapi()
        }else{
            Tools.enableLoc(activity)
        }

    }


    override fun callapi() {
if (Current) {
    async = Getcitybylocation_async(model = this@My_weather_model)
    Tools.async(async!!)
}else{
    async = Getweatherbycity(model = this@My_weather_model,city = city!!.cityname,type =2 )
    Tools.async(async!!)
}}

    override fun getallapis() {
        citiesasync= Getallcities_async(model = this@My_weather_model)
        Tools.async(citiesasync!!)
    }

    override fun cancelapi() {

           if (async!=null&&async!!.getStatus() == AsyncRq.Status.RUNNING) {
               async!!.cancel()
           }


           if (citiesasync!=null&&citiesasync!!.getStatus() == AsyncRq.Status.RUNNING) {
               citiesasync!!.cancel()
           }

    }

    override fun showloading(show: Boolean) {
     if (show){
         fclass.load.visibility=View.VISIBLE
         fclass.weathers_list.visibility=View.GONE
         fclass.cityname.visibility=View.GONE
         fclass.C_or_f.visibility=View.GONE
     }else{
         fclass.load.visibility=View.GONE
         fclass.weathers_list.visibility=View.VISIBLE
         fclass.cityname.visibility=View.VISIBLE
         fclass.C_or_f.visibility=View.VISIBLE
     }
    }

    override fun handleui() {
        val mLayoutManager: RecyclerView.LayoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        fclass.weathers_list.layoutManager = mLayoutManager
        fclass.weathers_list.itemAnimator = DefaultItemAnimator()
        val mLayoutManagerh: RecyclerView.LayoutManager =  LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        fclass.cities_list.layoutManager = mLayoutManagerh
        fclass.cities_list.itemAnimator = DefaultItemAnimator()


        fclass.C_or_f.setOnClickListener(changetype())
    }

    override fun getloaction() {
        location = Tools.getlastKnownLocation(activity,this@My_weather_model)
    }

    override fun showdata(city: City) {
this.city=city

        if (city.cityname!=null&&!city.cityname.isNullOrEmpty()&& city.cityname!!.trim().isNotEmpty()){
            fclass.cityname.text=city.cityname
        }
      if (city.weather.size>0) {
          addadapter()
      }

    }

    override fun addadapter() {
        var weatherAdapter: Weather_adapter = Weather_adapter(model = this@My_weather_model)
    fclass.weathers_list.adapter=weatherAdapter

        if (fclass.cities_list.adapter==null){
            getallapis()
        }else{
            showloading(false)
        }
    }

    override fun parsejosn(output: String): City {
        val city: City = City()
        if (output.trim().isNotEmpty()){
            val json: JSONObject = JSONObject(output)
            if (json.has("city")){
                val cityjson: JSONObject =json.getJSONObject("city")
                if (cityjson.has("id")){
                    city.cityid=cityjson.getString("id")
                }
                if (cityjson.has("name")){
                    city.cityname=cityjson.getString("name")
                }
                if (cityjson.has("country")){
                    city.country=cityjson.getString("country")
                }
                if (cityjson.has("country")){
                    city.country=cityjson.getString("country")
                }
            }
            if (json.has("list")){
                val list: JSONArray =json.getJSONArray("list")
                val weatherList: ArrayList<Weather> = ArrayList<Weather>()

                for (i in 0 until list.length()) {
                    val weather: Weather = Weather()
                    val item: JSONObject = list.getJSONObject(i)
                    if (item.has("dt")){
                        weather.date=item.getString("dt")
                    }
                    if (item.has("weather")){
                        val jsonwea: JSONObject =item.getJSONArray ("weather").getJSONObject(0)
                        if (jsonwea.has("main")){
                            weather.mainwea=jsonwea.getString("main")
                        }
                        if (jsonwea.has("description")){
                            weather.description=jsonwea.getString("description")
                        }
                        if (jsonwea.has("icon")){
                            weather.icon=jsonwea.getString("icon")
                        }
                        if (jsonwea.has("id")){
                            weather.weaid=jsonwea.getString("id")
                        }
                    }
                    if (item.has("main")){
                        val jsontemp: JSONObject =item.getJSONObject("main")
                        if (jsontemp.has("temp")){
weather.temp=jsontemp.getString("temp")
                        }
                        if (jsontemp.has("temp_min")){
                            weather.temp_min=jsontemp.getString("temp_min")
                        }
                        if (jsontemp.has("temp_max")){
                            weather.temp_max=jsontemp.getString("temp_max")
                        }
                    }
                    weatherList.add(weather)
                }
                city.weather.addAll(weatherList)
            }

        }


        return city
    }

    override fun changetype(): View.OnClickListener {
        return View.OnClickListener {
            temptype = if (temptype=="metric") { "imperial" }else{ "metric" }
            callapi()
        }
    }

    override fun handlecitiesui(cities: ArrayList<City>) {


            if (CitiesList.size>0){
                CitiesList.clear()
            }
      CitiesList.add(city!!)
        if (cities.size>0) {
            CitiesList.addAll(cities)
        }

            var adapter:Citiesadapter= Citiesadapter(this@My_weather_model)
            fclass.cities_list.adapter=adapter


    }



    override fun onLocationChanged(p0: Location) {

    }

   override fun showadd_popup(): View.OnClickListener {
        return View.OnClickListener {
            var addpop: Dialog = Dialog(activity!!)
            addpop.requestWindowFeature(Window.FEATURE_NO_TITLE)
            addpop.setContentView(R.layout.add_city_popup)



            addpop.show()
            var add: Button = addpop.findViewById(R.id.button2)
            var cancel: Button = addpop.findViewById(R.id.cancel)
            var location: Button = addpop.findViewById(R.id.location)
            var cityname: EditText = addpop.findViewById(R.id.cityname)
            cancel.setOnClickListener(View.OnClickListener {
                addpop.cancel()
            })
            location.setOnClickListener(View.OnClickListener {
         Current=true
          callapi()
addpop.cancel()
            })

            add.setOnClickListener(addcityname(cityname, addpop))
        }
    }
    override fun addcityname(cityname:EditText?, addpop:Dialog): View.OnClickListener {

        return View.OnClickListener {
            if (cityname!=null&&cityname.text!=null&& cityname.text!!.trim().isNotEmpty()) {
                getcitynamewea(cityname.text!!.toString())
                addpop.cancel()
            }else{
                Toast.makeText(activity, "Enter city", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun addcitydata(city: City) {
        if ((CitiesList.size==0||(CitiesList.size>0&&!Tools.havecity(CitiesList,city)))){
            Tools.async(AddorRemovecity_async(this@My_weather_model,true,0,city))
        }else{
           showdata(city)
        }

    }

    override fun handleaddorremove(city: City,boolean: Boolean,postion:Int) {
        if (!boolean&&fclass.cities_list.adapter!=null){

            var adapter: Citiesadapter = fclass.cities_list.adapter as Citiesadapter
            CitiesList.removeAt(postion)
            adapter.notifyItemRemoved(postion)
            adapter.notifyItemRangeRemoved(postion,CitiesList.size)
            showcity_weather(null)
        } else if (boolean){
            if (fclass.cities_list.adapter==null){

                CitiesList.add(city)


                var adapter:Citiesadapter= Citiesadapter(this@My_weather_model)
                fclass.cities_list.adapter=adapter
            }else {

                var adapter: Citiesadapter = fclass.cities_list.adapter as Citiesadapter
                CitiesList.add(city)
                adapter.notifyDataSetChanged()
            }
            showcity_weather(city)
        }
    }


    fun getcitynamewea(cityname:String?){
        Tools.async(Getweatherbycity(this,cityname,2))
    }



    fun showcity_weather(city: City?){

        Current=false
        if(city!=null){
            if (city.cityname!=null){
                fclass.cityname.text=city.cityname
            }
            this.city=city

        }else{
            Current=true

        }
        callapi()
    }

}