package com.nutricent.app.model

import java.time.LocalDate

enum class MealType(val label: String) {
    BREAKFAST("Sarapan"),
    LUNCH("Makan Siang"),
    DINNER("Makan Malam"),
    SNACK("Cemilan")
}

data class FoodLog(
    val id: Int,
    val name: String,
    val mealType: MealType,
    val calories: Int,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val price: Int,
    val notes: String,
    val date: LocalDate,
    val imageRes: Int,
    val isHomemade: Boolean
)

data class UserProfile(
    val name: String,
    val email: String,
    val dailyCalorieTarget: Int,
    val dailyBudget: Int
)

data class DummyAccount(
    val email: String,
    val password: String
)
