package com.nutricent.app.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nutricent.app.data.DummyData
import com.nutricent.app.model.FoodLog
import com.nutricent.app.model.MealType
import java.time.LocalDate

/**
 * ViewModel BERSAMA (shared) untuk Home, Jurnal, Detail, Scanner.
 * Dibuat sekali di AppNavHost (scope Activity). Di screen cukup terima parameternya,
 * JANGAN buat instance baru per screen.
 */
class FoodLogViewModel : ViewModel() {

    private val _foods = mutableStateListOf<FoodLog>().apply { addAll(DummyData.foodLogs) }

    val userProfile = DummyData.userProfile

    // ---------- State ----------
    var selectedDate: LocalDate by mutableStateOf(DummyData.DEFAULT_DATE)
        private set

    /** null = "Semua" */
    var mealFilter: MealType? by mutableStateOf(null)
        private set

    // ---------- Derived state (otomatis update) ----------
    /** Semua makanan di tanggal terpilih, urut sarapan -> cemilan. */
    val foodsOfSelectedDate: List<FoodLog> by derivedStateOf {
        _foods.filter { it.date == selectedDate }.sortedBy { it.mealType.ordinal }
    }

    /** Untuk Jurnal: tanggal terpilih + filter meal. */
    val filteredFoods: List<FoodLog> by derivedStateOf {
        mealFilter?.let { type -> foodsOfSelectedDate.filter { it.mealType == type } }
            ?: foodsOfSelectedDate
    }

    val totalCalories: Int by derivedStateOf { foodsOfSelectedDate.sumOf { it.calories } }
    val totalSpent: Int by derivedStateOf { foodsOfSelectedDate.sumOf { it.price } }

    /** Bisa negatif kalau melebihi target. */
    val remainingCalories: Int by derivedStateOf { userProfile.dailyCalorieTarget - totalCalories }
    val remainingBudget: Int by derivedStateOf { userProfile.dailyBudget - totalSpent }

    val isOverCalorie: Boolean by derivedStateOf { totalCalories > userProfile.dailyCalorieTarget }
    val isOverBudget: Boolean by derivedStateOf { totalSpent > userProfile.dailyBudget }

    /** 0f..1f untuk indikator progress. */
    val calorieProgress: Float by derivedStateOf {
        (totalCalories.toFloat() / userProfile.dailyCalorieTarget).coerceIn(0f, 1f)
    }
    val budgetProgress: Float by derivedStateOf {
        (totalSpent.toFloat() / userProfile.dailyBudget).coerceIn(0f, 1f)
    }

    // ---------- Actions ----------
    fun selectDate(date: LocalDate) { selectedDate = date }

    fun selectMealFilter(type: MealType?) { mealFilter = type }

    fun foodsOf(type: MealType): List<FoodLog> = foodsOfSelectedDate.filter { it.mealType == type }

    /** Dipakai Detail. Mengembalikan null kalau tidak ketemu -> tampilkan "Data tidak ditemukan". */
    fun getFoodById(id: Int): FoodLog? =
        _foods.find { it.id == id }
            ?: if (id == DummyData.SCANNED_FOOD_ID) DummyData.scannedFood else null

    /**
     * Simpan hasil edit Detail.
     * - id sudah ada di jurnal  -> update
     * - id belum ada (hasil scan) -> tambah item baru di tanggal terpilih
     */
    fun saveFood(food: FoodLog) {
        val index = _foods.indexOfFirst { it.id == food.id }
        if (index >= 0) {
            _foods[index] = food
        } else {
            val newId = (_foods.maxOfOrNull { it.id } ?: 0) + 1
            _foods.add(food.copy(id = newId, date = selectedDate))
        }
    }

    fun deleteFood(id: Int) { _foods.removeAll { it.id == id } }
}
