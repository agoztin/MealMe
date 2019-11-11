package com.example.mealme.net

import com.example.mealme.model.Meals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDBService {

    @GET("search.php")
    fun searchByMeal(@Query("s") mealName: String): Call<Meals>
}