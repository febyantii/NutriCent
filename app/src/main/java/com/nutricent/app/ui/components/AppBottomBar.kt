
package com.nutricent.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class BottomTab {
    HOME,
    JOURNAL,
    ANALYTICS,
    PROFILE
}

@Composable
fun AppBottomBar(
    selected: BottomTab,
    onHomeClick: () -> Unit,
    onJournalClick: () -> Unit,
    onScanClick: () -> Unit,
    onComingSoon: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    // Warna dari theme, otomatis menyesuaikan dark mode
    val navbarBackground = colors.surface
    val scanButtonColor = colors.primary
    val scanIconColor = colors.onPrimary

    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .height(100.dp)
    ) {

        // ==========================================
        // NAVBAR BACKGROUND
        // ==========================================

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 6.dp,
                    spotColor = colors.onSurface.copy(alpha = 0.10f),
                    ambientColor = colors.onSurface.copy(alpha = 0.05f)
                ),
            color = navbarBackground
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 6.dp,
                        bottom = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                NavbarItem(
                    icon = Icons.Filled.Home,
                    label = "Beranda",
                    selected = selected == BottomTab.HOME,
                    onClick = onHomeClick,
                    modifier = Modifier.weight(1f)
                )

                NavbarItem(
                    icon = Icons.Filled.MenuBook,
                    label = "Jurnal",
                    selected = selected == BottomTab.JOURNAL,
                    onClick = onJournalClick,
                    modifier = Modifier.weight(1f)
                )

                // Space untuk tombol scan
                Spacer(
                    modifier = Modifier.width(76.dp)
                )

                NavbarItem(
                    icon = Icons.Filled.BarChart,
                    label = "Analitik",
                    selected = selected == BottomTab.ANALYTICS,
                    onClick = onComingSoon,
                    modifier = Modifier.weight(1f)
                )

                NavbarItem(
                    icon = Icons.Filled.Person,
                    label = "Profil",
                    selected = selected == BottomTab.PROFILE,
                    onClick = onComingSoon,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // ==========================================
        // FLOATING SCAN BUTTON
        // ==========================================

        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 0.dp)
                .size(80.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    spotColor = scanButtonColor.copy(alpha = 0.35f),
                    ambientColor = scanButtonColor.copy(alpha = 0.18f)
                )
                .clickable(onClick = onScanClick),
            shape = CircleShape,
            color = navbarBackground,
            shadowElevation = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .background(
                        color = scanButtonColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Scan makanan",
                    tint = scanIconColor,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    }
}

// ==========================================
// NAVIGATION ITEM
// ==========================================

@Composable
private fun NavbarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    // Aktif: primary, inactive: onSurfaceVariant
    // Tidak menggunakan alpha agar teks tetap terbaca
    val itemColor = if (selected) {
        colors.primary
    } else {
        colors.onSurfaceVariant
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick)
            .padding(
                top = 7.dp,
                bottom = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = itemColor,
            modifier = Modifier.size(25.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = itemColor,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Indikator di bawah item aktif
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(3.dp)
                .background(
                    color = if (selected) {
                        colors.primary
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape
                )
        )
    }
}
