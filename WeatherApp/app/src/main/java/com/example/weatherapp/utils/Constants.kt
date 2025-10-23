package com.example.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Constants{

    const val BASE_URL = "https://api.openweathermap.org/data/"
    const val API_KEY = "1d1d30b3cbf6dcdcbb488d41f6c4b50c"
    const val METRIC_UNIT = "metric"
    fun isNetworkAvailable(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= 23){
            val network = connectivityManager.activeNetwork?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->  return true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->  return true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->  return true
                else -> return false
            }
        }else{
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }

        return false
    }
}