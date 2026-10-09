
package com.nutricent.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nutricent.app.R

// Font Plus Jakarta Sans
// Menggunakan satu file font yang tersedia.
val NutriCentFontFamily: FontFamily = FontFamily(
    Font(
        resId = R.font.plus_jakarta_sans,
        weight = FontWeight.Normal
    )
)

private fun style(
    weight: FontWeight,
    size: Int,
    line: Int,
    spacing: Double = 0.0
) = TextStyle(
    fontFamily = NutriCentFontFamily,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = line.sp,
    letterSpacing = spacing.sp
)

val NutriCentTypography = Typography(
    headlineLarge = style(FontWeight.Bold, 32, 40),
    headlineMedium = style(FontWeight.Bold, 26, 34),
    headlineSmall = style(FontWeight.SemiBold, 22, 30),

    titleLarge = style(FontWeight.SemiBold, 20, 28),
    titleMedium = style(FontWeight.SemiBold, 16, 24, 0.15),
    titleSmall = style(FontWeight.Medium, 14, 20, 0.1),

    bodyLarge = style(FontWeight.Normal, 16, 24, 0.3),
    bodyMedium = style(FontWeight.Normal, 14, 20, 0.2),
    bodySmall = style(FontWeight.Normal, 12, 16, 0.3),

    labelLarge = style(FontWeight.SemiBold, 14, 20, 0.1),
    labelMedium = style(FontWeight.Medium, 12, 16, 0.5),
    labelSmall = style(FontWeight.Medium, 11, 16, 0.5)
)