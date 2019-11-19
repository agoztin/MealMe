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

    val meals = MutableLiveData<ArrayList<Meal>?>()
    var lastSearch: Call<Meals>? = null

    var searchResult = ArrayList<Meal>()
    var favourites = ArrayList<Meal>()

    fun search(mealName: String) {
        meals.value = null
        // Web Service call
        lastSearch = ApiService.instance.searchByMeal(mealName)
        lastSearch?.enqueue(object: Callback<Meals> {
            override fun onFailure(call: Call<Meals>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
                Log.e(TAG, "Detail: ${t.stackTrace.toList()}")
            }

            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                if (response.isSuccessful) {
                    searchResult = response.body()?.meals!!
                    meals.value = searchResult
                }
            }
        })
    }

    fun cancelSearch() {
        if (lastSearch != null)
            lastSearch?.cancel()
    }

    suspend fun save(meal: Meal) {
        mealsDao.insert(meal)
        meal.ingredients.forEach {
            it.idMeal = meal.id
        }
        ingredientDao.insertIngredients(meal.ingredients)
    }

    suspend fun load(mealID: Int): Meal? {
        val meal = mealsDao.get(mealID)
        meal?.ingredients = ingredientDao.get(mealID) as ArrayList<Ingredient>
        return meal
    }

    suspend fun delete(meal: Meal) {
        mealsDao.delete(meal.id)
        ingredientDao.delete(meal.id)
    }

    suspend fun loadFavourites() {
        val mealsLoaded = mealsDao.getAll()
        mealsLoaded.forEach { meal ->
            meal.ingredients = ingredientDao.get(meal.id) as ArrayList<Ingredient>
        }
        favourites = mealsLoaded as ArrayList<Meal>
        meals.value = favourites
    }

    fun loadSearchResult() {
        meals.value = searchResult
    }
}