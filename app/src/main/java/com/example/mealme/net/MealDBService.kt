package com.example.mealme.net

import com.example.mealme.model.Meals
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDBService {

    companion object {
        val URL = "https://www.themealdb.com/api/json/v1/1/"
    }

    @GET("search.php")
    suspend fun searchByMeal(@Query("s") mealName: String): Meals
}