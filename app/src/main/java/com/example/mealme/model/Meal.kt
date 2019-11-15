package com.example.mealme.model

import android.util.Log
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Meal (
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var category: String = "",
    var instructions: String = "",
    var thumbURL: String = "",
    @Ignore
    var ingredients: ArrayList<Ingredient> = ArrayList()
) {

    @delegate:Ignore
    val imageFileName by lazy {
        id.toString() + thumbURL.takeLastWhile { it != '/' }
    }
}