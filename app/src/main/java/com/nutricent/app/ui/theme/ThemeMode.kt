
package com.nutricent.app.ui.theme

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

object ThemePreferences {

    private const val PREF_FILE =
        "nutricent_theme_preferences"

    private const val KEY_THEME_MODE = "theme_mode"

    private var initialized = false

    var mode by mutableStateOf(ThemeMode.SYSTEM)
        private set

    fun initialize(context: Context) {
        if (initialized) return

        val saved = context.applicationContext
            .getSharedPreferences(
                PREF_FILE,
                Context.MODE_PRIVATE
            )
            .getString(
                KEY_THEME_MODE,
                ThemeMode.SYSTEM.name
            )

        mode = ThemeMode.values()
            .firstOrNull { it.name == saved }
            ?: ThemeMode.SYSTEM

        initialized = true
    }

    fun setMode(
        context: Context,
        newMode: ThemeMode
    ) {
        initialize(context)

        mode = newMode

        context.applicationContext
            .getSharedPreferences(
                PREF_FILE,
                Context.MODE_PRIVATE
            )
            .edit()
            .putString(KEY_THEME_MODE, newMode.name)
            .apply()
    }
}
