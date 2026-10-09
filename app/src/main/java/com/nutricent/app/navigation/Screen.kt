package com.nutricent.app.navigation

/** Semua route aplikasi ada di sini. Jangan tulis string route manual di screen. */
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Journal : Screen("journal")
    data object Scanner : Screen("scanner")

    data object Detail : Screen("detail/{foodId}") {
        const val ARG_FOOD_ID = "foodId"
        fun createRoute(foodId: Int) = "detail/$foodId"
    }
}
