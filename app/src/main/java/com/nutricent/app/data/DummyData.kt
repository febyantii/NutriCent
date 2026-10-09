package com.nutricent.app.data

import com.nutricent.app.R
import com.nutricent.app.model.DummyAccount
import com.nutricent.app.model.FoodLog
import com.nutricent.app.model.MealType
import com.nutricent.app.model.UserProfile
import java.time.LocalDate

/**
 * Sumber data dummy tunggal. JANGAN hardcode data di composable.
 * Ganti PLACEHOLDER_IMAGE dengan drawable makanan asli (mis. R.drawable.img_oatmeal).
 */
object DummyData {

    // Minggu tampilan: Senin 19 - Minggu 25 Oktober 2026 (sesuaikan dengan mockup bila beda)
    val WEEK_START: LocalDate = LocalDate.of(2026, 10, 19)
    val weekDates: List<LocalDate> = (0..6).map { WEEK_START.plusDays(it.toLong()) }

    // Tanggal default saat aplikasi dibuka (Jumat 23): tidak ada sarapan/cemilan -> bisa tes empty state
    val DEFAULT_DATE: LocalDate = LocalDate.of(2026, 10, 23)

    // ID khusus hasil scan (tidak ada di daftar jurnal)
    const val SCANNED_FOOD_ID = 99

    private val PLACEHOLDER_IMAGE = R.drawable.ic_launcher_foreground

    val userProfile = UserProfile(
        name = "Feby",
        email = "feby@nutricent.com",
        dailyCalorieTarget = 2000,
        dailyBudget = 60_000
    )

    // Akun demo untuk Login. Sign Up akan menambah ke list ini (in-memory).
    val accounts: MutableList<DummyAccount> = mutableListOf(
        DummyAccount("feby@nutricent.com", "feby123"),
        DummyAccount("demo@nutricent.com", "123456")
    )

    private fun f(
        id: Int,
        name: String,
        meal: MealType,
        kcal: Int,
        carbs: Int,
        protein: Int,
        fat: Int,
        price: Int,
        dayOffset: Int,
        imageRes: Int,
        notes: String = "",
        homemade: Boolean = false
    ) = FoodLog(
        id = id,
        name = name,
        mealType = meal,
        calories = kcal,
        carbs = carbs,
        protein = protein,
        fat = fat,
        price = price,
        notes = notes,
        date = WEEK_START.plusDays(dayOffset.toLong()),
        imageRes = imageRes,
        isHomemade = homemade
    )

    val foodLogs: List<FoodLog> = listOf(
        // Senin 19 - hari normal, 4 jenis meal
        f( 1, "Oatmeal Bowl", MealType.BREAKFAST, 320, 52, 11, 6, 15_000, 0, R.drawable.img_oatmeal, notes = "Topping pisang dan madu" ),
        f( 2, "Nasi Ayam Bakar", MealType.LUNCH, 480, 58, 32, 14, 24_000, 0, R.drawable.img_nasi_ayam_bakar ),
        f( 3, "Salad Sayur + Telur", MealType.DINNER, 300, 18, 20, 16, 20_000, 0, R.drawable.img_salad_sayur_telur ),
        f( 4, "Green Smoothie", MealType.SNACK, 120, 24, 3, 2, 0, 0, R.drawable.img_green_smoothie, notes = "Bayam, apel, pisang", homemade = true ),
        // Selasa 20
        f( 5, "Nasi Goreng Telur Ayam", MealType.BREAKFAST, 580, 72, 22, 24, 20_000, 1, R.drawable.img_nasi_goreng ),
        f( 6, "Soto Ayam", MealType.LUNCH, 450, 40, 30, 18, 18_000, 1, R.drawable.img_soto_ayam ),
        f( 7, "Pisang Rebus", MealType.SNACK, 110, 28, 1, 0, 0, 1, R.drawable.img_pisang_rebus, homemade = true ),
        // Rabu 21 -> SENGAJA KOSONG (empty state)
        // Kamis 22 - MELEBIHI kalori (2.510) dan budget (Rp85.000)
        f( 8, "Oatmeal Pisang Homemade", MealType.BREAKFAST, 340, 58, 10, 7, 0, 3, R.drawable.img_oatmeal_pisang, homemade = true ),
        f( 9, "Nasi Padang Rendang", MealType.LUNCH, 850, 90, 38, 36, 32_000, 3, R.drawable.img_nasi_padang_rendang ),
        f( 10, "Mie Goreng Seafood", MealType.DINNER, 720, 85, 28, 28, 28_000, 3, R.drawable.img_mie_goreng_seafood ),
        f( 11, "Martabak Manis", MealType.SNACK, 600, 80, 10, 26, 25_000, 3, R.drawable.img_martabak_manis, notes = "Tengah malam, khilaf" ),
        // Jumat 23 - hanya makan siang & malam
        f( 12, "Nasi Goreng Telur Ayam", MealType.LUNCH, 580, 72, 22, 24, 20_000, 4, R.drawable.img_nasi_goreng ),
        f( 13, "Gado-gado", MealType.DINNER, 380, 35, 15, 20, 17_000, 4, R.drawable.img_gado_gado ),
        // Sabtu 24
        f( 14, "Roti Bakar Telur", MealType.BREAKFAST, 290, 34, 12, 11, 12_000, 5, R.drawable.img_roti_bakar_telur ),
        f( 15, "Ayam Geprek", MealType.LUNCH, 620, 60, 35, 26, 22_000, 5, R.drawable.img_ayam_geprek ),
        f( 16, "Smoothie Bowl", MealType.SNACK, 180, 30, 5, 5, 15_000, 5, R.drawable.img_smoothie_bowl ),
        // Minggu 25
        f( 17, "Bubur Ayam", MealType.BREAKFAST, 350, 55, 14, 8, 14_000, 6, R.drawable.img_bubur_ayam ),
        f( 18, "Sup Sayur Homemade", MealType.LUNCH, 220, 25, 8, 9, 0, 6, R.drawable.img_sup_sayur_homemade, homemade = true )
    )

    /** Hasil scan di Food Scanner (id = SCANNED_FOOD_ID). Belum tersimpan di jurnal. */
    val scannedFood = FoodLog(
        id = SCANNED_FOOD_ID, name = "Nasi Goreng Telur Ayam", mealType = MealType.LUNCH,
        calories = 580, carbs = 72, protein = 22, fat = 24, price = 20_000,
        notes = "", date = DEFAULT_DATE, imageRes = R.drawable.img_nasi_goreng, isHomemade = false
    )
}
