package com.nutricent.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
    HOME, JOURNAL, ANALYTICS, PROFILE
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
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp)
    ) {
        // Background navbar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 6.dp,
                    spotColor = colorScheme.onSurface.copy(alpha = 0.10f),
                    ambientColor = colorScheme.onSurface.copy(alpha = 0.05f)
                ),
            color = colorScheme.surfaceContainerLowest
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 7.dp
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

                // Ruang kosong untuk tombol scan di tengah
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

        // Floating scan button
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 0.dp)
                .size(80.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    spotColor = colorScheme.primary.copy(alpha = 0.30f),
                    ambientColor = colorScheme.primary.copy(alpha = 0.15f)
                )
                .clickable(onClick = onScanClick),
            shape = CircleShape,
            color = colorScheme.surfaceContainerLowest,
            shadowElevation = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .background(
                        color = colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Scan makanan",
                    tint = colorScheme.onPrimary,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
private fun NavbarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    val itemColor = if (selected) {
        colorScheme.primary
    } else {
        colorScheme.onSurfaceVariant
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick)
            .padding(top = 9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = itemColor,
            modifier = Modifier.size(27.dp)
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            color = itemColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Indikator menu aktif
        Box(
            modifier = Modifier
                .width(22.dp)
                .height(3.dp)
                .background(
                    color = if (selected) {
                        colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape
                )
        )
    }
}

