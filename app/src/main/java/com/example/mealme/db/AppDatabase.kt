package com.example.mealme.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mealme.model.Ingredient
import com.example.mealme.model.Meal

@Database(entities = arrayOf(
    Meal::class,
    Ingredient::class
), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun ingredientDao(): IngredientDao
}