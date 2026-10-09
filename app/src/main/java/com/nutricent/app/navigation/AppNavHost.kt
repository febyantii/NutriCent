package com.nutricent.app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nutricent.app.data.DummyData
import com.nutricent.app.ui.screens.detail.DetailScreen
import com.nutricent.app.ui.screens.home.HomeScreen
import com.nutricent.app.ui.screens.journal.JournalScreen
import com.nutricent.app.ui.screens.login.LoginScreen
import com.nutricent.app.ui.screens.scanner.ScannerScreen
import com.nutricent.app.viewmodel.FoodLogViewModel

/** Pindah antar tab utama (Home/Jurnal) tanpa menumpuk back stack. */
fun NavController.navigateTopLevel(route: String) {
    navigate(route) {
        popUpTo(Screen.Home.route)
        launchSingleTop = true
    }
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    // Shared ViewModel: dibuat SEKALI di sini (scope Activity), dioper ke semua screen.
    val foodLogViewModel: FoodLogViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                onAuthSuccess = {
                    navController.navigate(Screen.Home.route) {
                        // Hapus login dari back stack: tombol back tidak kembali ke login
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                foodLogViewModel = foodLogViewModel,
                onNavigateToJournal = { navController.navigateTopLevel(Screen.Journal.route) },
                onNavigateToScanner = { navController.navigate(Screen.Scanner.route) },
                onNavigateToDetail = { foodId ->
                    navController.navigate(Screen.Detail.createRoute(foodId))
                }
            )
        }

        composable(Screen.Journal.route) {
            JournalScreen(
                foodLogViewModel = foodLogViewModel,
                onNavigateToHome = { navController.navigateTopLevel(Screen.Home.route) },
                onNavigateToScanner = { navController.navigate(Screen.Scanner.route) },
                onNavigateToDetail = { foodId ->
                    navController.navigate(Screen.Detail.createRoute(foodId))
                }
            )
        }

        composable(Screen.Scanner.route) {
            ScannerScreen(
                onBack = { navController.popBackStack() },
                onScanFinished = {
                    navController.navigate(Screen.Detail.createRoute(DummyData.SCANNED_FOOD_ID)) {
                        // Scanner dibuang dari back stack: setelah Save di Detail
                        // pengguna kembali ke layar asal (Home/Jurnal), bukan ke Scanner.
                        popUpTo(Screen.Scanner.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(Screen.Detail.ARG_FOOD_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt(Screen.Detail.ARG_FOOD_ID) ?: -1
            DetailScreen(
                foodId = foodId,
                foodLogViewModel = foodLogViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
