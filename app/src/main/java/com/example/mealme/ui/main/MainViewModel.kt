package com.example.mealme.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mealme.db.AppDatabase
import com.example.mealme.model.Meal
import com.example.mealme.net.repositories.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val mealsRepository: MealsRepository
    val selectedMeal = MutableLiveData<Meal>()

    init {
        val db = AppDatabase.getInstance(application)
        val mealsDao = db.mealDao()
        val ingredientDao = db.ingredientDao()
        mealsRepository = MealsRepository(mealsDao, ingredientDao)
    }

    fun getSearchResult(): LiveData<ArrayList<Meal>> {
        return mealsRepository.searchData
    }

    fun searchMeals(mealName: String) {
        mealsRepository.search(mealName)
    }

    fun saveMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealsRepository.save(meal)
        }
    }

    fun selectMeal(meal: Meal) {
        selectedMeal.value = meal
    }
}
