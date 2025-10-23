package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.services.WeatherService
import com.example.weatherapp.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private val requestLocationCode = 111
    private lateinit var mFusedLocationCLient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mFusedLocationCLient = LocationServices.getFusedLocationProviderClient(this)

        if (!isLocationEnabled()) {
            Toast.makeText(this@MainActivity, "Please Provide Location", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            requestPermission()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestLocationCode && grantResults.isNotEmpty()) {
//            Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
            requestLocationData()
        } else {
            Toast.makeText(this@MainActivity, "Permission Needed", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationData() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        Toast.makeText(this, "Requesting location...", Toast.LENGTH_SHORT).show()

        mFusedLocationCLient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if(location != null){
                    getLocationWeatherDetails(location.latitude,location.longitude)
                }else{
                    Toast.makeText(this@MainActivity, "No location found!", Toast.LENGTH_SHORT).show()
                }
            }
        }, Looper.myLooper())
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showRequestDialog() {
        AlertDialog.Builder(this).setPositiveButton("Go to settings") { _, _ ->
            {
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
        }.setNegativeButton("Close") { dialog, _ -> dialog.cancel() }
            .setTitle("Location Permission Needed")
            .setMessage("This permission is needed for accessing location")
            .show()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            requestLocationCode
        )
    }


    private fun getLocationWeatherDetails(latitude: Double, longitude: Double) {
        if (Constants.isNetworkAvailable(this)) {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val weatherServiceAPI = retrofit.create(WeatherService::class.java)
            val call = weatherServiceAPI.getWeatherDetails(
                latitude, longitude, Constants.API_KEY,
                Constants.METRIC_UNIT
            )

            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val weatherResponse = response.body()!!
                        Log.d("WeatherResponse", Gson().toJson(weatherResponse))

                        findViewById<TextView>(R.id.tv_sunset).text = convertTime(weatherResponse.sys.sunset.toLong())
                        findViewById<TextView>(R.id.tv_sunrise).text = convertTime(weatherResponse.sys.sunset.toLong())
                        findViewById<TextView>(R.id.tvMaxTemp).text = weatherResponse.main.temp_max.toString()
                        findViewById<TextView>(R.id.tvMinTemp).text = weatherResponse.main.temp_min.toString()
                        findViewById<TextView>(R.id.tvTemperature).text = weatherResponse.main.temp.toString()
                        findViewById<TextView>(R.id.tv_humidity).text = weatherResponse.main.humidity.toString()
                        findViewById<TextView>(R.id.tv_pressure).text = weatherResponse.main.pressure.toString()
                        findViewById<TextView>(R.id.tv_wind).text = weatherResponse.wind.speed.toString()
                        findViewById<TextView>(R.id.tvCity).text = weatherResponse.name

                        weatherResponse.weatherData.let { data ->
                            for (i in data.indices) {
                                findViewById<TextView>(R.id.tvCondition).text = data[i].description
                            }
                        }

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<WeatherResponse>,
                    t: Throwable
                ) {

                }

            })

        } else {
            Toast.makeText(this@MainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convertTime(time:Long):String{
        val date = Date(time * 1000L)
        val timeFormatted = SimpleDateFormat("HH:mm", Locale.UK)
        timeFormatted.timeZone = TimeZone.getDefault()
        return timeFormatted.format(date)
    }

}