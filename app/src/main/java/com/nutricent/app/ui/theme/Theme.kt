package com.nutricent.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


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

    surfaceContainerLowest = SurfaceLowestLight,
    surfaceContainerLow = NutriCardSurface,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceHighLight,
    surfaceContainerHighest = SurfaceHighestLight
)

private val DarkColors = darkColorScheme(
    primary = AvocadoGreenDark,
    onPrimary = OnAvocadoGreenDark,
    primaryContainer = AvocadoGreenContainerDark,
    onPrimaryContainer = OnAvocadoGreenContainerDark,

    secondary = CoinGoldDark,
    onSecondary = OnCoinGoldDark,
    secondaryContainer = CoinGoldContainerDark,
    onSecondaryContainer = OnCoinGoldContainerDark,

    tertiary = NutriTeal,
    onTertiary = OnNutriTeal,
    tertiaryContainer = AvocadoGreenContainerDark,
    onTertiaryContainer = OnAvocadoGreenContainerDark,

    background = DarkBackground,
    onBackground = OnDarkBackground,
    surface = SurfaceLowDark,
    onSurface = OnDarkBackground,
    surfaceVariant = DarkVariant,
    onSurfaceVariant = OnDarkVariant,

    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,

    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = ErrorDark,

    surfaceContainerLowest = SurfaceLowestDark,
    surfaceContainerLow = SurfaceLowDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceHighDark,
    surfaceContainerHighest = SurfaceHighestDark
)


/* ---------- LOGIN COLOR SCHEME EXTENSIONS ---------- */

val ColorScheme.loginBackground: Color
    get() = LoginBackground

val ColorScheme.loginDecoration: Color
    get() = LoginDecoration

val ColorScheme.loginSoftGreen: Color
    get() = LoginSoftGreen

val ColorScheme.loginBorder: Color
    get() = LoginBorder

val ColorScheme.loginGreen: Color
    get() = LoginGreen

val ColorScheme.loginDarkGreen: Color
    get() = LoginDarkGreen

val ColorScheme.loginDecorationLight: Color
    get() = LoginDecorationLight

val ColorScheme.loginDecorationPale: Color
    get() = LoginDecorationPale

val ColorScheme.loginDecorationBottom: Color
    get() = LoginDecorationBottom



// ---------- JOURNAL COLOR SCHEME EXTENSIONS ----------

val ColorScheme.journalGreen: Color
    get() = JournalGreen

val ColorScheme.journalDarkGreen: Color
    get() = JournalDarkGreen

val ColorScheme.journalMint: Color
    get() = JournalMint

val ColorScheme.journalBorder: Color
    get() = JournalBorder

val ColorScheme.journalText: Color
    get() = JournalText

val ColorScheme.journalGray: Color
    get() = JournalGray

val ColorScheme.journalDateBackground: Color
    get() = JournalDateBackground

val ColorScheme.journalSubtitle: Color
    get() = JournalSubtitle

val ColorScheme.journalActionRed: Color
    get() = JournalActionRed

val ColorScheme.journalMenuIcon: Color
    get() = JournalMenuIcon


/**
 * Bungkus SEMUA screen dengan NutriCentTheme.
 * Dynamic color sengaja TIDAK dipakai supaya warna brand tidak tertimpa wallpaper.
 */
@Composable
fun NutriCentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = NutriCentTypography,
        shapes = NutriCentShapes,
        content = content
    )
}
