package com.nutricent.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AvocadoGreen, onPrimary = OnAvocadoGreen,
    primaryContainer = AvocadoGreenContainer, onPrimaryContainer = OnAvocadoGreenContainer,
    secondary = CoinGold, onSecondary = OnCoinGold,
    secondaryContainer = CoinGoldContainer, onSecondaryContainer = OnCoinGoldContainer,
    tertiary = NutriTeal, onTertiary = OnNutriTeal,
    tertiaryContainer = TertiaryBrownContainer, onTertiaryContainer = OnTertiaryBrownContainer,
    background = Cream, onBackground = OnCream,
    surface = Cream, onSurface = OnCream,
    surfaceVariant = CreamVariant, onSurfaceVariant = OnCreamVariant,
    outline = OutlineLight, outlineVariant = OutlineVariantLight,
    error = ErrorLight, onError = OnErrorLight,
    errorContainer = ErrorContainerLight, onErrorContainer = OnErrorContainerLight,
    surfaceContainerLowest = SurfaceLowestLight,
    surfaceContainerLow = SurfaceLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceHighLight,
    surfaceContainerHighest = SurfaceHighestLight
)

private val DarkColors = darkColorScheme(
    primary = AvocadoGreenDark, onPrimary = OnAvocadoGreenDark,
    primaryContainer = AvocadoGreenContainerDark, onPrimaryContainer = OnAvocadoGreenContainerDark,
    secondary = CoinGoldDark, onSecondary = OnCoinGoldDark,
    secondaryContainer = CoinGoldContainerDark, onSecondaryContainer = OnCoinGoldContainerDark,
    tertiary = TertiaryBrownDark, onTertiary = OnTertiaryBrownDark,
    tertiaryContainer = TertiaryBrownContainerDark, onTertiaryContainer = OnTertiaryBrownContainerDark,
    background = DarkBackground, onBackground = OnDarkBackground,
    surface = DarkBackground, onSurface = OnDarkBackground,
    surfaceVariant = DarkVariant, onSurfaceVariant = OnDarkVariant,
    outline = OutlineDark, outlineVariant = OutlineVariantDark,
    error = ErrorDark, onError = OnErrorDark,
    errorContainer = ErrorContainerDark, onErrorContainer = OnErrorContainerDark,
    surfaceContainerLowest = SurfaceLowestDark,
    surfaceContainerLow = SurfaceLowDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceHighDark,
    surfaceContainerHighest = SurfaceHighestDark
)

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
