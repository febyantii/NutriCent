package com.nutricent.app.util

import android.util.Patterns

/** Semua fungsi mengembalikan pesan error (String) atau null kalau valid. */
object Validators {

    fun validateEmail(value: String): String? = when {
        value.isBlank() -> "Email wajib diisi"
        !Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches() -> "Format email tidak valid"
        else -> null
    }

    fun validatePassword(value: String): String? = when {
        value.isEmpty() -> "Password wajib diisi"
        value.length < 6 -> "Minimal 6 karakter"
        else -> null
    }

    // ---- Siap dipakai screen Detail (Anggota E) ----

    fun validateRequired(value: String, fieldName: String): String? =
        if (value.isBlank()) "$fieldName wajib diisi" else null

    fun validateNotes(value: String, max: Int = 100): String? =
        if (value.length > max) "Maksimal $max karakter" else null

    /** Harga: wajib, hanya digit, Rp0 boleh. */
    fun validatePrice(value: String): String? = when {
        value.isBlank() -> "Harga wajib diisi"
        !value.all { it.isDigit() } -> "Harga hanya boleh angka"
        value.toIntOrNull() == null -> "Angka terlalu besar"
        else -> null
    }

    /** Pakai di onValueChange field angka: onValueChange = { price = Validators.filterDigits(it) } */
    fun filterDigits(input: String): String = input.filter { it.isDigit() }
}
