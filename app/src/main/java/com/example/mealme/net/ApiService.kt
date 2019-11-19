package com.example.mealme.net

import com.example.mealme.model.Meal
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



object ApiService {

    private val URL = "https://www.themealdb.com/api/json/v1/1/"



    val instance: MealDBService by lazy {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

        val deserializer = GsonBuilder().registerTypeAdapter(Meal::class.java, MealDeserializer()).create()

        Retrofit.Builder()
            .baseUrl(URL)
//            .client(client)
            .addConverterFactory(GsonConverterFactory.create(deserializer))
            .build()
            .create(MealDBService::class.java)
    }
}