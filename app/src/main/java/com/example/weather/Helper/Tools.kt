package com.example.weather.Helper

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.FragmentActivity
import com.example.weather.Bean.City
import com.example.weather.Coroutine.AsyncRq
import com.example.weather.Viewmodel.My_weather_model
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
class Tools {
    companion object {
        var locationManager: LocationManager? = null
        private var REQUEST_LOCATION = 199
        private var masterasync: AsyncRq<String, Any>? = null
        fun async(asyncTask: AsyncRq<String, Any>) {
            if (masterasync != null && asyncTask != null && masterasync != null && masterasync === asyncTask && masterasync!!.getStatus() === AsyncRq.Status.RUNNING) {
                masterasync!!.cancel()
                if (asyncTask != null) {
                    asyncTask.execute<String?>()
                }
            } else if (asyncTask != null) {
                asyncTask.execute<String?>()
            }
            masterasync = asyncTask
        }

        fun getlastKnownLocation(activity: FragmentActivity,model:My_weather_model): Location? {
            var isEnabled = false
            var isenableg = false
            locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers: List<String> = locationManager!!.getProviders(true)
            var bestLocation: Location? = null
            for (provider in providers) {
                if (ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null
                }
                locationManager!!.requestLocationUpdates(
                    provider,
                    0,
                    0f,
                    (model as LocationListener?)!!
                )
                isEnabled = locationManager!!.isProviderEnabled(provider)
                if (isenableg == false) {
                    isenableg = locationManager!!.isProviderEnabled(provider)
                }
                if (isEnabled) {
                    val l: Location = locationManager!!.getLastKnownLocation(provider) ?: continue
                    if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                        // Found best last known location: %s", l);
                        bestLocation = l
                    }
                }
            }
            if (bestLocation == null && isenableg) {
                getlastKnownLocation(activity,model)
            } else {
                return bestLocation
            }
            return null
        }


        fun isLocationPermissionGranted(activity: FragmentActivity): Boolean {
            return if (Build.VERSION.SDK_INT >= 23) {
                if (PermissionChecker.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    == PermissionChecker.PERMISSION_GRANTED
                ) {
                    Log.e("permission", "[Permission is granted >=23")
                    true
                } else {
                    Log.e("permission", "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1
                    )
                    false
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.e("permission", "Permission is granted <23")
                true
            }
        }

        fun enableLoc(activity: FragmentActivity) {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 30 * 1000.toLong()
            locationRequest.fastestInterval = 5 * 1000.toLong()
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)
            val locationSettingsRequest = builder.build()
            val result =
                LocationServices.getSettingsClient(activity)
                    .checkLocationSettings(locationSettingsRequest)
            result.addOnCompleteListener { task ->
                try {
                    val response = task.getResult(
                        ApiException::class.java
                    )
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->                               // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                val resolvable =
                                    exception as ResolvableApiException
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                    activity,
                                    REQUEST_LOCATION
                                )
                            } catch (e: IntentSender.SendIntentException) {
                                // Ignore the error.
                            } catch (e: ClassCastException) {
                                // Ignore, should be an impossible error.
                            }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        }
                    }
                }
            }
        }

        fun checklocation(activity: FragmentActivity?): Boolean {
            var isEnabled = false
            locationManager =
                activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers: List<String> = locationManager!!.getProviders(true)
            for (provider in providers) {

                if (!isEnabled) {
                    isEnabled = locationManager!!.isProviderEnabled(provider)
                }

            }
            return isEnabled

        }

        fun havecity(lis:ArrayList<City>, city: City):Boolean{
            for (ci in lis){
                if (ci.cityid == city.cityid){
                    return true
                }
            }
            return false

        }


    }

}