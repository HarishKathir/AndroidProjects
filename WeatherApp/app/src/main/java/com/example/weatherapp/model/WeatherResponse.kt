package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val coord: Coord,
    @SerializedName("weather")
    val weatherData: MutableList<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
) {

}
