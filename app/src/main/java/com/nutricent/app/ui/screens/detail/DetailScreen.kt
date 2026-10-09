package com.nutricent.app.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutricent.app.util.formatRupiah
import com.nutricent.app.viewmodel.FoodLogViewModel

/**
 * STUB - Anggota E: bangun Detail + form edit sesuai mockup.
 * Pola wajib:
 *  - ambil data: foodLogViewModel.getFoodById(foodId) ; null -> "Data tidak ditemukan"
 *  - pre-fill: var name by remember(foodId) { mutableStateOf(food.name) }
 *  - validasi: util/Validators (validateRequired, validateNotes, validatePrice, filterDigits)
 *  - simpan: foodLogViewModel.saveFood(food.copy(...)) lalu onBack()
 */
@Composable
fun DetailScreen(
    foodId: Int,
    foodLogViewModel: FoodLogViewModel,
    onBack: () -> Unit
) {
    val food = remember(foodId) { foodLogViewModel.getFoodById(foodId) }

    Scaffold { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            TextButton(onClick = onBack) { Text("< Kembali") }

            if (food == null) {
                Text("Data tidak ditemukan", style = MaterialTheme.typography.titleLarge)
            } else {
                Text("TODO: Detail (Anggota E)", style = MaterialTheme.typography.headlineMedium)
                Text("foodId = $foodId")
                Text("${food.name} - ${food.calories} kcal - ${formatRupiah(food.price)}")
                Button(onClick = {
                    // Tes state: kalori +10 lalu kembali -> cek Home/Jurnal ikut berubah
                    foodLogViewModel.saveFood(food.copy(calories = food.calories + 10))
                    onBack()
                }) { Text("Tes Save (+10 kcal)") }
            }
        }
    }
}
