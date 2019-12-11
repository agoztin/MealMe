package com.example.mealme.net

import com.example.mealme.model.Meals
import com.google.gson.JsonObject
import retrofit2.http.*


interface MealDBService {

    @GET("search.php")
    suspend fun searchByMeal(@Query("s") mealName: String): Meals

    @POST("insert")
    suspend fun postMeal(@Body meal: JsonObject): Meals

}