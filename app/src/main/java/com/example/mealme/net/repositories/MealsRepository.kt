package com.example.mealme.net.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mealme.db.IngredientDao
import com.example.mealme.db.MealDao
import com.example.mealme.model.Ingredient
import com.example.mealme.model.Meal
import com.example.mealme.model.Meals
import com.example.mealme.net.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsRepository(val mealsDao: MealDao, val ingredientDao: IngredientDao) {

    val TAG = this.javaClass.name

    val searchData = MutableLiveData<ArrayList<Meal>>()

    fun search(mealName: String) {
        // Web Service call
        ApiService.instance.searchByMeal(mealName).enqueue(object: Callback<Meals> {
            override fun onFailure(call: Call<Meals>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
                Log.e(TAG, "Detail: ${t.stackTrace.toList()}")
            }

            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                if (response.isSuccessful) {
                    searchData.value = response.body()?.meals
                }
            }
        })
    }

    suspend fun save(meal: Meal) {
        mealsDao.insert(meal)
        ingredientDao.insertIngredients(meal.ingredients)
    }

    suspend fun load(mealID: Int): Meal? {
        val meal = mealsDao.get(mealID)
        meal?.ingredients = ingredientDao.getIngredients(mealID) as ArrayList<Ingredient>
        return meal
    }

    suspend fun delete(meal: Meal) {
        mealsDao.delete(meal.id)
    }
}