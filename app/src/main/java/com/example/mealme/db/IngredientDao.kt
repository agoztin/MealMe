package com.example.mealme.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.mealme.model.Ingredient

@Dao
interface IngredientDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM Ingredient WHERE id = :mealId")
    fun getIngredients(mealId: Int): LiveData<List<Ingredient>>

    @Transaction
    suspend fun insertIngredients(ingredients: ArrayList<Ingredient>) {
        ingredients.forEach {
            insertIngredient(it)
        }
    }
}
