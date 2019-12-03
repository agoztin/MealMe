package com.example.mealme.model

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("meals")
    val meals: ArrayList<Meal>? = ArrayList()
) {
}