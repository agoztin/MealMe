package com.example.mealme.net.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mealme.db.IngredientDao
import com.example.mealme.db.MealDao
import com.example.mealme.model.Ingredient
import com.example.mealme.model.Meal
import com.example.mealme.net.MealDBService
import java.lang.Exception
import javax.inject.Inject

class MealsRepository
    @Inject
    constructor(val mealsDao: MealDao, val ingredientDao: IngredientDao, val apiService: MealDBService) {

    val TAG = this.javaClass.name
    var searchResult: ArrayList<Meal>? = null
    var mealsList = MutableLiveData<ArrayList<Meal>?>()


    suspend fun search(mealName: String): ArrayList<Meal>? {
        searchResult = null
        mealsList.value = null

        try {
            val lastSearch = apiService.searchByMeal(mealName)
            searchResult = lastSearch.meals ?: ArrayList()

            mealsList.value = searchResult

            return searchResult
        } catch (e: Exception) {
            return ArrayList()
        }
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
        mealsList.value = mealsLoaded as ArrayList<Meal>
    }

    fun loadSearchResult() {
        mealsList.value = searchResult
    }
}