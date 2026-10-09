package com.nutricent.app.util

import java.util.Locale
import kotlin.math.abs

private val ID_LOCALE: Locale = Locale.forLanguageTag("id-ID")

/** 34000 -> "Rp34.000", -5000 -> "-Rp5.000" */
fun formatRupiah(amount: Int): String {
    val number = String.format(ID_LOCALE, "%,d", abs(amount))
    return (if (amount < 0) "-" else "") + "Rp$number"
}

/** 1100 -> "1.100 kcal" */
fun formatKcal(value: Int): String = String.format(ID_LOCALE, "%,d kcal", value)
