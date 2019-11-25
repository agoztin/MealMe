package com.example.mealme.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mealme.db.AppDatabase
import com.example.mealme.model.Meal
import com.example.mealme.net.repositories.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var mealsRepository: MealsRepository
    val selectedMeal = MutableLiveData<Meal>()


    fun getMealsLiveData(): LiveData<ArrayList<Meal>?> = mealsRepository.meals

    fun searchMeals(mealName: String) = viewModelScope.launch {
        mealsRepository.search(mealName)
    }

    fun cancelSearch() = mealsRepository.cancelSearch()

    fun saveMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        mealsRepository.save(meal)
    }

    fun deleteMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        mealsRepository.delete(meal)
    }

    fun selectMeal(meal: Meal) {
        selectedMeal.value = meal
    }

    fun loadMeal(mealID: Int): LiveData<Meal?> = liveData {
        emit(mealsRepository.load(mealID))
    }

    fun loadFavouritesMeals() = viewModelScope.launch {
        mealsRepository.loadFavourites()
    }

    fun loadSearchResult() = mealsRepository.loadSearchResult()
}
