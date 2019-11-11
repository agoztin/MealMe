package com.example.mealme.model

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Meal (
    @SerializedName("idMeal")
    val id: Int = 0,
    @SerializedName("strMeal")
    val name: String = "",
    @SerializedName("strCategory")
    val category: String = "",
    @SerializedName("strInstructions")
    val instructions: String = "",
    @SerializedName("strMealThumb")
    val thumbURL: String = "",
    @SerializedName("strIngredient1")
    private val _ing1: String = "",
    @SerializedName("strIngredient2")
    private val _ing2: String = "",
    @SerializedName("strIngredient3")
    private val _ing3: String = "",
    @SerializedName("strIngredient4")
    private val _ing4: String = "",
    @SerializedName("strIngredient5")
    private val _ing5: String = "",
    @SerializedName("strIngredient6")
    private val _ing6: String = "",
    @SerializedName("strIngredient7")
    private val _ing7: String = "",
    @SerializedName("strIngredient8")
    private val _ing8: String = "",
    @SerializedName("strIngredient9")
    private val _ing9: String = "",
    @SerializedName("strMeasure1")
    private val _meas1: String = "",
    @SerializedName("strMeasure2")
    private val _meas2: String = "",
    @SerializedName("strMeasure3")
    private val _meas3: String = "",
    @SerializedName("strMeasure4")
    private val _meas4: String = "",
    @SerializedName("strMeasure5")
    private val _meas5: String = "",
    @SerializedName("strMeasure6")
    private val _meas6: String = "",
    @SerializedName("strMeasure7")
    private val _meas7: String = "",
    @SerializedName("strMeasure8")
    private val _meas8: String = "",
    @SerializedName("strMeasure9")
    private val _meas9: String = ""
) {

    val ingredients: ArrayList<Ingredient> by lazy {
        ArrayList<Ingredient>().apply {
            add(Ingredient(_ing1, _meas1))
            add(Ingredient(_ing2, _meas2))
            add(Ingredient(_ing3, _meas3))
            add(Ingredient(_ing4, _meas4))
            add(Ingredient(_ing5, _meas5))
            add(Ingredient(_ing6, _meas6))
            add(Ingredient(_ing7, _meas7))
            add(Ingredient(_ing8, _meas8))
            add(Ingredient(_ing9, _meas9))
        }
    }
}