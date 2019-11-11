package com.example.mealme.net.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mealme.model.Meal
import com.example.mealme.model.Meals
import com.example.mealme.net.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsRepository {

    val TAG = this.javaClass.name

    val searchData = MutableLiveData<ArrayList<Meal>>()

    fun search(mealName: String) {

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
}