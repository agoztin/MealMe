package com.example.mealme.db

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.mealme.model.Ingredient

@Dao
interface IngredientDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(ingredient: Ingredient)

    @Query("SELECT * FROM Ingredient WHERE idMeal = :mealId")
    suspend fun get(mealId: Int): List<Ingredient>

    @Query("DELETE FROM Ingredient WHERE idMeal = :mealId")
    suspend fun delete(mealId: Int)

    @Transaction
    suspend fun insertIngredients(ingredients: ArrayList<Ingredient>) {
        ingredients.forEach {
            insert(it)
        }
    }
}
