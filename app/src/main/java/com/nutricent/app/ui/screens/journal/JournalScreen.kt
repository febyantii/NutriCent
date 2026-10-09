package com.nutricent.app.ui.screens.journal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutricent.app.ui.components.AppBottomBar
import com.nutricent.app.ui.components.BottomTab
import com.nutricent.app.viewmodel.FoodLogViewModel
import kotlinx.coroutines.launch

/**
 * STUB - Anggota D: bangun Jurnal sesuai mockup (LazyRow tanggal, FilterChip, section header, dst).
 * Pola argumen yang BENAR: klik item -> onNavigateToDetail(food.id).
 * Data: filteredFoods, selectDate(), selectMealFilter(), totalCalories, totalSpent.
 */
@Composable
fun JournalScreen(
    foodLogViewModel: FoodLogViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AppBottomBar(
                selected = BottomTab.JOURNAL,
                onHomeClick = onNavigateToHome,
                onJournalClick = {},
                onScanClick = onNavigateToScanner,
                onComingSoon = { scope.launch { snackbarHostState.showSnackbar("Segera hadir") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            Text(
                "TODO: Jurnal (Anggota D) - ${foodLogViewModel.selectedDate}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn {
                items(foodLogViewModel.filteredFoods, key = { it.id }) { food ->
                    Text(
                        text = "${food.name} - ${food.calories} kcal",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToDetail(food.id) }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
