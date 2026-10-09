package com.nutricent.app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.nutricent.app.util.formatKcal
import com.nutricent.app.util.formatRupiah
import com.nutricent.app.viewmodel.FoodLogViewModel
import kotlinx.coroutines.launch

/**
 * STUB - Anggota C: ganti isi Column dengan layout Home sesuai mockup.
 * JANGAN ubah signature parameter (sudah terhubung ke AppNavHost).
 */
@Composable
fun HomeScreen(
    foodLogViewModel: FoodLogViewModel,
    onNavigateToJournal: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AppBottomBar(
                selected = BottomTab.HOME,
                onHomeClick = {},
                onJournalClick = onNavigateToJournal,
                onScanClick = onNavigateToScanner,
                onComingSoon = { scope.launch { snackbarHostState.showSnackbar("Segera hadir") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("TODO: Home (Anggota C)", style = MaterialTheme.typography.headlineMedium)
            Text("Kalori: ${formatKcal(foodLogViewModel.totalCalories)} / " +
                    formatKcal(foodLogViewModel.userProfile.dailyCalorieTarget))
            Text("Sisa budget: ${formatRupiah(foodLogViewModel.remainingBudget)}")
            Button(onClick = {
                foodLogViewModel.foodsOfSelectedDate.firstOrNull()?.let { onNavigateToDetail(it.id) }
            }) { Text("Tes: buka detail item pertama") }
        }
    }
}
