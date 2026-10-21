
package com.nutricent.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ==============================================
// LIGHT COLOR SCHEME
// ==============================================

private val LightColors = lightColorScheme(
    primary = AvocadoGreen,
    onPrimary = OnAvocadoGreen,
    primaryContainer = AvocadoGreenContainer,
    onPrimaryContainer = OnAvocadoGreenContainer,

    secondary = CoinGold,
    onSecondary = OnCoinGold,
    secondaryContainer = CoinGoldContainer,
    onSecondaryContainer = OnCoinGoldContainer,

    tertiary = NutriTeal,
    onTertiary = OnNutriTeal,
    tertiaryContainer = NutriBadgeContainer,
    onTertiaryContainer = NutriDarkGreen,

    // Background putih bersih saat light mode
    background = NutriScreenBackground,
    onBackground = OnCream,

    surface = NutriCardSurface,
    onSurface = OnCream,

    surfaceVariant = LoginSoftGreen,
    onSurfaceVariant = NutriDarkGreen,

    outline = OutlineLight,
    outlineVariant = LoginBorder,

    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,

    // Hindari cream pada surface utama
    surfaceContainerLowest = SurfaceLowestLight,
    surfaceContainerLow = NutriCardSurface,
    surfaceContainer = NutriCardSurface,
    surfaceContainerHigh = SurfaceHighLight,
    surfaceContainerHighest = SurfaceHighestLight
)

// ==============================================
// DARK COLOR SCHEME
// ==============================================

private val DarkColors = darkColorScheme(
    primary = NutriButtonGreenDark,
    onPrimary = Color(0xFF103B27),

    primaryContainer = AvocadoGreenContainerDark,
    onPrimaryContainer = OnAvocadoGreenContainerDark,

    secondary = CoinGoldDark,
    onSecondary = OnCoinGoldDark,
    secondaryContainer = CoinGoldContainerDark,
    onSecondaryContainer = OnCoinGoldContainerDark,

    tertiary = NutriTealDark,
    onTertiary = OnNutriTealDark,
    tertiaryContainer = NutriBadgeContainerDark,
    onTertiaryContainer = NutriDarkGreenDark,

    background = NutriScreenBackgroundDark,
    onBackground = JournalTextDark,

    surface = NutriCardSurfaceDark,
    onSurface = JournalTextDark,

    surfaceVariant = LoginSoftGreenDark,
    onSurfaceVariant = JournalSubtitleDark,

    outline = OutlineDark,
    outlineVariant = NutriBorderColorDark,

    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,

    surfaceContainerLowest = SurfaceLowestDark,
    surfaceContainerLow = SurfaceLowDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceHighDark,
    surfaceContainerHighest = SurfaceHighestDark
)

// ==============================================
// THEME MODE HELPER
// ==============================================

// Cek dari warna background scheme aktif.
// Lebih aman daripada perbandingan referensi objek.

private val ColorScheme.isNutriDark: Boolean
    get() = background == DarkColors.background

// ==============================================
// GLOBAL NUTRICENT EXTENSIONS
// ==============================================

val ColorScheme.nutriScreenBackground: Color
    get() = if (isNutriDark)
        NutriScreenBackgroundDark
    else
        NutriScreenBackground

val ColorScheme.nutriCardSurface: Color
    get() = if (isNutriDark)
        NutriCardSurfaceDark
    else
        NutriCardSurface

val ColorScheme.nutriTeal: Color
    get() = if (isNutriDark)
        NutriTealDark
    else
        NutriTeal

val ColorScheme.nutriOnTeal: Color
    get() = if (isNutriDark)
        OnNutriTealDark
    else
        OnNutriTeal

val ColorScheme.nutriDarkGreen: Color
    get() = if (isNutriDark)
        NutriDarkGreenDark
    else
        NutriDarkGreen

val ColorScheme.nutriButtonGreen: Color
    get() = if (isNutriDark)
        NutriButtonGreenDark
    else
        NutriButtonGreen

val ColorScheme.nutriBadgeContainer: Color
    get() = if (isNutriDark)
        NutriBadgeContainerDark
    else
        NutriBadgeContainer

val ColorScheme.nutriBorderColor: Color
    get() = if (isNutriDark)
        NutriBorderColorDark
    else
        NutriBorderColor

// ==============================================
// LOGIN SCREEN EXTENSIONS
// ==============================================

val ColorScheme.loginBackground: Color
    get() = if (isNutriDark)
        LoginBackgroundDark
    else
        LoginBackground

val ColorScheme.loginDecoration: Color
    get() = if (isNutriDark)
        LoginDecorationDark
    else
        LoginDecoration

val ColorScheme.loginSoftGreen: Color
    get() = if (isNutriDark)
        LoginSoftGreenDark
    else
        LoginSoftGreen

val ColorScheme.loginBorder: Color
    get() = if (isNutriDark)
        LoginBorderDark
    else
        LoginBorder

val ColorScheme.loginGreen: Color
    get() = if (isNutriDark)
        LoginGreenDarkMode
    else
        LoginGreen

val ColorScheme.loginDarkGreen: Color
    get() = if (isNutriDark)
        LoginDarkGreenDarkMode
    else
        LoginDarkGreen

val ColorScheme.loginDecorationLight: Color
    get() = if (isNutriDark)
        LoginDecorationLightDark
    else
        LoginDecorationLight

val ColorScheme.loginDecorationPale: Color
    get() = if (isNutriDark)
        LoginDecorationPaleDark
    else
        LoginDecorationPale

val ColorScheme.loginDecorationBottom: Color
    get() = if (isNutriDark)
        LoginDecorationBottomDark
    else
        LoginDecorationBottom

// ==============================================
// JOURNAL SCREEN EXTENSIONS
// ==============================================

val ColorScheme.journalGreen: Color
    get() = if (isNutriDark)
        JournalGreenDarkMode
    else
        JournalGreen

val ColorScheme.journalDarkGreen: Color
    get() = if (isNutriDark)
        JournalDarkGreenDarkMode
    else
        JournalDarkGreen

val ColorScheme.journalMint: Color
    get() = if (isNutriDark)
        JournalMintDark
    else
        JournalMint

val ColorScheme.journalBorder: Color
    get() = if (isNutriDark)
        JournalBorderDark
    else
        JournalBorder

val ColorScheme.journalText: Color
    get() = if (isNutriDark)
        JournalTextDark
    else
        JournalText

val ColorScheme.journalGray: Color
    get() = if (isNutriDark)
        JournalGrayDark
    else
        JournalGray

val ColorScheme.journalDateBackground: Color
    get() = if (isNutriDark)
        JournalDateBackgroundDark
    else
        JournalDateBackground

val ColorScheme.journalSubtitle: Color
    get() = if (isNutriDark)
        JournalSubtitleDark
    else
        JournalSubtitle

val ColorScheme.journalActionRed: Color
    get() = if (isNutriDark)
        JournalActionRedDark
    else
        JournalActionRed

val ColorScheme.journalMenuIcon: Color
    get() = if (isNutriDark)
        JournalMenuIconDark
    else
        JournalMenuIcon

// ==============================================
// HOME DASHBOARD EXTENSIONS
// ==============================================

val ColorScheme.homeGreenSoft: Color
    get() = if (isNutriDark)
        HomeGreenSoftDark
    else
        HomeGreenSoft

val ColorScheme.homeGreenTrack: Color
    get() = if (isNutriDark)
        HomeGreenTrackDark
    else
        HomeGreenTrack

val ColorScheme.homeTextSecondary: Color
    get() = if (isNutriDark)
        HomeTextSecondaryDark
    else
        HomeTextSecondary

val ColorScheme.homeNotificationRed: Color
    get() = if (isNutriDark)
        HomeNotificationRedDark
    else
        HomeNotificationRed

val ColorScheme.homeRecommendationYellow: Color
    get() = if (isNutriDark)
        HomeRecommendationYellowDark
    else
        HomeRecommendationYellow

// ==============================================
// FOOD CATEGORY EXTENSIONS
// ==============================================

// ---------- BREAKFAST ----------

val ColorScheme.breakfastBackground: Color
    get() = if (isNutriDark)
        BreakfastBackgroundDark
    else
        BreakfastBackground

val ColorScheme.breakfastBorder: Color
    get() = if (isNutriDark)
        BreakfastBorderDark
    else
        BreakfastBorder

val ColorScheme.breakfastChip: Color
    get() = if (isNutriDark)
        BreakfastChipDark
    else
        BreakfastChip

// ---------- LUNCH ----------

val ColorScheme.lunchBackground: Color
    get() = if (isNutriDark)
        LunchBackgroundDark
    else
        LunchBackground

val ColorScheme.lunchBorder: Color
    get() = if (isNutriDark)
        LunchBorderDark
    else
        LunchBorder

val ColorScheme.lunchChip: Color
    get() = if (isNutriDark)
        LunchChipDark
    else
        LunchChip

// ---------- DINNER ----------

val ColorScheme.dinnerBackground: Color
    get() = if (isNutriDark)
        DinnerBackgroundDark
    else
        DinnerBackground

val ColorScheme.dinnerBorder: Color
    get() = if (isNutriDark)
        DinnerBorderDark
    else
        DinnerBorder

val ColorScheme.dinnerChip: Color
    get() = if (isNutriDark)
        DinnerChipDark
    else
        DinnerChip

// ---------- SNACK ----------

val ColorScheme.snackBackground: Color
    get() = if (isNutriDark)
        SnackBackgroundDark
    else
        SnackBackground

val ColorScheme.snackBorder: Color
    get() = if (isNutriDark)
        SnackBorderDark
    else
        SnackBorder

val ColorScheme.snackChip: Color
    get() = if (isNutriDark)
        SnackChipDark
    else
        SnackChip

// ==============================================
// MAIN APP THEME
// ==============================================

/**
 * Theme NutriCent:
 *
 * SYSTEM -> mengikuti dark mode Android
 * LIGHT  -> memaksa light mode
 * DARK   -> memaksa dark mode
 *
 * Preferensi disimpan oleh ThemePreferences.
 * Perubahan state akan memicu recomposition.
 *
 * Dynamic color tidak digunakan supaya warna
 * brand NutriCent tidak berubah mengikuti wallpaper.
 */

@Composable
fun NutriCentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current.applicationContext

    // Initialize preferensi saat root theme dibuat.
    // remember mencegah pembacaan ulang setiap recomposition.
    remember(context) {
        ThemePreferences.initialize(context)
        true
    }

    // State theme yang diamati Compose
    val selectedMode = ThemePreferences.mode

    // Tentukan tema berdasarkan pilihan pengguna
    val isDarkTheme = when (selectedMode) {
        ThemeMode.SYSTEM -> darkTheme
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    // Pilih color scheme aktif
    val colors = if (isDarkTheme) {
        DarkColors
    } else {
        LightColors
    }

    // Terapkan ke seluruh aplikasi
    MaterialTheme(
        colorScheme = colors,
        typography = NutriCentTypography,
        shapes = NutriCentShapes,
        content = content
    )
}
