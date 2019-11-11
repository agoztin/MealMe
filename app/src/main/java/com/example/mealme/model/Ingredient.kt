package com.example.mealme.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class Ingredient(
    val name: String = "",
    val measure: String = ""
) {
}