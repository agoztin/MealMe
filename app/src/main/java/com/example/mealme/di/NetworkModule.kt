package com.example.mealme.di

import com.example.mealme.BuildConfig
import com.example.mealme.model.Meal
import com.example.mealme.net.MealDBService
import com.example.mealme.net.MealDeserializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMelaDBService(): MealDBService {
        val gson = GsonBuilder().registerTypeAdapter(Meal::class.java, MealDeserializer()).create()
        val converterFactory = GsonConverterFactory.create(gson)

        val retrofitBuilder =  Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(converterFactory)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val okHttpClient = OkHttpClient.Builder().apply {
                connectTimeout(5, TimeUnit.SECONDS)
                addInterceptor(httpLoggingInterceptor)
            }.build()
            retrofitBuilder.client(okHttpClient)
        }

        return retrofitBuilder.build().create(MealDBService::class.java)
    }


}