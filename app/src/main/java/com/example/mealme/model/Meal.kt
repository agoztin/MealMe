package com.example.mealme.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id"])])
data class Meal (
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var category: String = "",
    var tags: String = "",
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