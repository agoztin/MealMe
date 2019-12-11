package com.example.mealme.net.repositories

import androidx.lifecycle.MutableLiveData
import com.example.mealme.db.IngredientDao
import com.example.mealme.db.MealDao
import com.example.mealme.model.Ingredient
import com.example.mealme.model.Meal
import com.example.mealme.net.MealDBService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.lang.Exception
import javax.inject.Inject

class MealsRepository
    @Inject
    constructor(val mealsDao: MealDao, val ingredientDao: IngredientDao, val apiService: MealDBService) {

    val TAG = this.javaClass.name
    var searchResult: ArrayList<Meal>? = null
    var mealsList = MutableLiveData<RepositoryResult<ArrayList<Meal>?>>()


    suspend fun search(mealName: String) {
        searchResult = null
        mealsList.value = RepositoryResult.loading()
        try {
            val lastSearch = apiService.searchByMeal(mealName)
            searchResult = lastSearch.meals ?: ArrayList()

            mealsList.value = RepositoryResult.success(searchResult)
        } catch (e: Exception) {
            mealsList.value = RepositoryResult.error("${e.message}")
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
        mealsList.value = RepositoryResult.loading()
        val mealsLoaded = mealsDao.getAll()
        mealsLoaded.forEach { meal ->
            meal.ingredients = ingredientDao.get(meal.id) as ArrayList<Ingredient>
        }
        mealsList.value = RepositoryResult.success(mealsLoaded as ArrayList<Meal>)
    }


    fun loadSearchResult() {
        mealsList.value = if (searchResult == null) RepositoryResult.loading() else RepositoryResult.success(searchResult)
    }


    suspend fun postMeal(meal: Meal): RepositoryResult<Meal> {
        try {
            val bodyMeal = JsonObject().apply {
                addProperty("strMeal", meal.name)
                addProperty("strDrinkAlternate", "")
                addProperty("strCategory", meal.category)
                addProperty("strArea", "")
                addProperty("strInstructions", meal.instructions)
                addProperty("strMealThumb", meal.thumbURL)
                addProperty("strTags", meal.tags)
                addProperty("strYoutube", "")
                for (i in 1..20) {
                    addProperty("strIngredient$i", meal.ingredients[i-1].name)
                    addProperty("strMeasure$i", meal.ingredients[i-1].measure)
                }
                addProperty("strSource", "")
            }

            val body = JsonObject().apply {
                add("meals", JsonArray().apply { add(bodyMeal) })
            }

            val result = apiService.postMeal(body)

            return RepositoryResult.success(result.meals!![0])
        } catch (e: Exception) {
            return RepositoryResult.error("Error: ${e.message}")
        }
    }
}