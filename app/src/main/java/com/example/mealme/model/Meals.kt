package com.example.mealme.model

import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("meals")
    val meals: ArrayList<Meal>? = ArrayList()
)