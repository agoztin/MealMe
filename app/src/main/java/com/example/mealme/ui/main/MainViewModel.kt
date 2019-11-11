package com.example.mealme.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mealme.model.Meal
import com.example.mealme.net.repositories.MealsRepository

class MainViewModel : ViewModel() {

    val mealsRepository = MealsRepository()

    fun getSearchResult(): LiveData<ArrayList<Meal>> {
        return mealsRepository.searchData
    }

    fun searchMeals(mealName: String) {
        mealsRepository.search(mealName)
    }
}
