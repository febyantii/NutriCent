package com.nutricent.app.ui.screens.scanner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nutricent.app.R
import com.nutricent.app.ui.theme.nutriButtonGreen
import kotlinx.coroutines.delay

@Composable
fun ScannerScreen(
    onBack: () -> Unit,
    onScanFinished: () -> Unit
) {
    var isFlashOn by remember { mutableStateOf(false) }
    var isScanning by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "LaserTransition")
    val laserYOffset by infiniteTransition.animateFloat(
        initialValue = -280f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LaserY"
    )

    LaunchedEffect(isScanning) {
        if (isScanning) {
            delay(1800)
            onScanFinished()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_nasi_goreng),
            contentDescription = "Camera Stream Mockup",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (isScanning) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .align(Alignment.Center)
                    .offset(y = laserYOffset.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.nutriButtonGreen,
                                Color.White,
                                MaterialTheme.colorScheme.nutriButtonGreen,
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .size(42.dp)
                    .clickable { onBack() }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.logo_nutricent),
                contentDescription = "NutriCent Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(100.dp)
                    .width(160.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .size(42.dp)
                    .clickable { isFlashOn = !isFlashOn }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                        contentDescription = "Toggle Flash",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = "Switch Camera",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 130.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.maskot_nutricent),
                contentDescription = "Mascot",
                modifier = Modifier.size(90.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = if (isScanning) "Menganalisis makananmu..." else "Arahkan kamera ke piring makananmu",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = isScanning,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-40).dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.nutriButtonGreen)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Menganalisis Makanan...",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .align(Alignment.BottomCenter),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .border(4.dp, MaterialTheme.colorScheme.nutriButtonGreen, CircleShape)
                        .clickable(enabled = !isScanning) { isScanning = true },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.nutriButtonGreen)
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ScannerScreenPreview() {
    com.nutricent.app.ui.theme.NutriCentTheme {
        ScannerScreen(
            onBack = {},
            onScanFinished = {}
        )
    }
}
