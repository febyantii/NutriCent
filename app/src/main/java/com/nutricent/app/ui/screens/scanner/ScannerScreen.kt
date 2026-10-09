package com.nutricent.app.ui.screens.scanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * STUB - Anggota E: bangun UI Scanner sesuai mockup (UI statis, TANPA CameraX/izin kamera).
 * Pola loading + navigasi di bawah sudah benar, tinggal dipertahankan.
 */
@Composable
fun ScannerScreen(
    onBack: () -> Unit,
    onScanFinished: () -> Unit
) {
    var isScanning by remember { mutableStateOf(false) }

    LaunchedEffect(isScanning) {
        if (isScanning) {
            delay(1800)          // simulasi "menganalisis makanan"
            onScanFinished()     // -> Detail/{SCANNED_FOOD_ID}
        }
    }

    Scaffold { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            TextButton(onClick = onBack) { Text("< Kembali") }
            Text("TODO: Food Scanner (Anggota E)", style = MaterialTheme.typography.headlineMedium)
            if (isScanning) {
                CircularProgressIndicator()
                Text("Menganalisis makanan…")
            }
            Button(onClick = { isScanning = true }, enabled = !isScanning) { Text("Shutter (tes)") }
        }
    }
}
