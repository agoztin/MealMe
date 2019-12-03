package com.example.mealme.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.mealme.db.AppDatabase
import com.example.mealme.db.IngredientDao
import com.example.mealme.db.MealDao
import com.example.mealme.net.MealDBService
import com.example.mealme.net.repositories.MealsRepository
import com.example.mealme.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    internal fun provideMealsRepository(mealDao: MealDao, ingredientDao: IngredientDao, mealDBService: MealDBService): MealsRepository {
        return MealsRepository(mealDao, ingredientDao, mealDBService)
    }

    @Provides
    @Singleton
    internal fun provideViewModel(mealsRepository: MealsRepository): MainViewModel {
        return MainViewModel(mealsRepository)
    }
}