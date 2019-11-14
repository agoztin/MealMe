package com.example.mealme.net

import android.util.Log
import com.example.mealme.model.Ingredient
import com.example.mealme.model.Meal
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MealDeserializer : JsonDeserializer<Meal> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Meal {
        var meal: Meal? = null
        val jsonObj = json?.asJsonObject // Every response come inside "meals" node

        if (jsonObj != null && !jsonObj.isJsonNull) {
            meal = Meal(
                jsonObj.get("idMeal").asInt,
                jsonObj.get("strMeal").asString,
                jsonObj.get("strCategory").asString,
                jsonObj.get("strInstructions").asString,
                jsonObj.get("strMealThumb").asString
            )
            for (i in 1 until 19) {
                var ing = ""
                var mes = ""
                if (!jsonObj.get("strIngredient$i").isJsonNull)
                    ing = jsonObj.get("strIngredient$i").asString
                if (!jsonObj.get("strMeasure$i").isJsonNull)
                    mes = jsonObj.get("strMeasure$i").asString
                meal.ingredients.add(Ingredient(meal.id, ing, mes))
            }
        }

        return meal ?:  Meal()
    }
}