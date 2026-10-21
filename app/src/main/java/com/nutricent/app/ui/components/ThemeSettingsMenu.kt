
package com.nutricent.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nutricent.app.ui.theme.ThemeMode
import com.nutricent.app.ui.theme.ThemePreferences

@Composable
fun ThemeSettingsMenu() {

    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    var expanded by remember {
        mutableStateOf(false)
    }

    val selectedMode = ThemePreferences.mode

    Box {
        IconButton(
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Pengaturan tema",
                tint = colors.onSurface
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            containerColor = colors.surface
        ) {

            Text(
                text = "Tema Tampilan",
                style = MaterialTheme.typography.titleSmall,
                color = colors.onSurface,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                )
            )

            val modes = listOf(
                ThemeMode.SYSTEM to "Ikuti sistem",
                ThemeMode.LIGHT to "Light mode",
                ThemeMode.DARK to "Dark mode"
            )

            modes.forEach { (mode, label) ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = label,
                            color = colors.onSurface
                        )
                    },

                    trailingIcon = {
                        if (selectedMode == mode) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Terpilih",
                                tint = colors.primary
                            )
                        }
                    },

                    onClick = {
                        expanded = false

                        ThemePreferences.setMode(
                            context,
                            mode
                        )
                    }
                )
            }
        }
    }
}
