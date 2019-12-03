package com.example.mealme.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealme.model.Ingredient
import com.example.mealme.model.Meal

@Database(entities = arrayOf(
    Meal::class,
    Ingredient::class
), version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun ingredientDao(): IngredientDao
}