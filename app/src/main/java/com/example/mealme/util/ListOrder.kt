package com.example.mealme.util

class ListOrder {
    enum class ORDER {
        ASC, DESC
    }

    enum class FIELD {
        SHUFFLE, NAME, CATEGORY
    }

    var order = ORDER.ASC
    var field = FIELD.SHUFFLE
        set(value) {
            if (value == FIELD.SHUFFLE) {
                order = ORDER.ASC
            }
            field = value
        }
}