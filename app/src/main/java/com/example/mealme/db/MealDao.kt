package com.example.mealme.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mealme.model.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal)

    @Query("SELECT * FROM Meal WHERE id = :id")
    suspend fun get(id: Int): Meal?

    @Query("DELETE FROM Meal WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM Meal")
    suspend fun getAll(): List<Meal>
}