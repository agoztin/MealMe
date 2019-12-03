package com.example.mealme.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["idMeal"])],
    foreignKeys = [
        ForeignKey(
            entity = Meal::class,
            parentColumns = ["id"],
            childColumns = ["idMeal"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class Ingredient(
    var idMeal: Int = 0,
    var name: String = "",
    var measure: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}