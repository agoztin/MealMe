package com.example.mealme.di

import android.app.Application
import androidx.room.Room
import com.example.mealme.db.AppDatabase
import com.example.mealme.db.IngredientDao
import com.example.mealme.db.MealDao
import com.example.mealme.model.Meal
import com.example.mealme.net.repositories.MealsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java, "database.db")
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideMealsRepository(mealDao: MealDao, ingredientDao: IngredientDao): MealsRepository {
        return MealsRepository(mealDao, ingredientDao)
    }

    @Provides
    @Singleton
    internal fun provideMealDao(appDatabase: AppDatabase): MealDao {
        return appDatabase.mealDao()
    }

    @Provides
    @Singleton
    internal fun provideIngredientDao(appDatabase: AppDatabase): IngredientDao {
        return appDatabase.ingredientDao()
    }
}