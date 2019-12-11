package com.example.mealme.viewmodel

import androidx.lifecycle.*
import com.example.mealme.model.Meal
import com.example.mealme.net.repositories.MealsRepository
import com.example.mealme.net.repositories.RepositoryResult
import com.example.mealme.util.ListOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class MainViewModel @Inject constructor(var mealsRepository: MealsRepository) : ViewModel() {

    val selectedMeal = MutableLiveData<Meal>()
    var listOrder = ListOrder()
    val mealsList = mealsRepository.mealsList


    fun searchMeals(mealName: String) = viewModelScope.launch {
        mealsRepository.search(mealName)
    }

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

    fun loadSearchResult() = viewModelScope.launch {
        mealsRepository.loadSearchResult()
    }

    fun postMeal(meal: Meal): LiveData<Result<String>> = liveData {
        val result = mealsRepository.postMeal(meal)
        when(result.status) {
            RepositoryResult.Status.SUCCESS -> emit(Result.success("Meal added with id ${result.data?.id}"))
            else -> emit(Result.failure(Exception(result.message ?: "Unknown error")))
        }
    }
}
