package com.nutricent.app.ui.screens.home

import androidx.compose.ui.tooling.preview.Preview
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutricent.app.ui.components.AppBottomBar
import com.nutricent.app.ui.components.BottomTab
import com.nutricent.app.ui.theme.NutriCentTheme
import com.nutricent.app.util.formatRupiah
import com.nutricent.app.viewmodel.FoodLogViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

// ---------- Warna ----------
private val GreenPrimary = Color(0xFF3E9E75)
private val GreenDark = Color(0xFF1F4D3A)
private val GreenSoft = Color(0xFFF3FAF6)
private val GreenBorder = Color(0xFFD3EBDD)
private val GreenTrack = Color(0xFFD6F0E2)
private val TextGray = Color(0xFF6B7280)

// ---------- Model UI ----------
data class MenuItemUi(
    val title: String,
    val kcal: Int,
    val background: Color,
    val border: Color,
    val chipColor: Color,
    @param:DrawableRes val imageRes: Int? = null
)

// TODO: nanti ganti dengan data asli dari ViewModel
private val dummyMenus = listOf(
    MenuItemUi("Breakfast", 350, Color(0xFFFFF9DB), Color(0xFFF3D77A), Color(0xFFFFEFB0)),
    MenuItemUi("Lunch", 520, Color(0xFFE9F8EF), Color(0xFF8ED1AC), Color(0xFFCDEEDA)),
    MenuItemUi("Dinner", 420, Color(0xFFF3EBFF), Color(0xFFC7A6FF), Color(0xFFE2D0FF)),
    MenuItemUi("Snack", 150, Color(0xFFFFEEE6), Color(0xFFF5B79A), Color(0xFFFFD9C6))
)

// ---------- Screen ----------
@Composable
fun HomeScreen(
    foodLogViewModel: FoodLogViewModel,
    onNavigateToJournal: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val comingSoon: () -> Unit = {
        scope.launch { snackbarHostState.showSnackbar("Segera hadir") }
    }

    // --- Data dari ViewModel ---
    val kaloriDikonsumsi = foodLogViewModel.totalCalories.toDouble()
    val kaloriTarget = foodLogViewModel.userProfile.dailyCalorieTarget.toDouble()
    val kaloriProgress = if (kaloriTarget > 0) (kaloriDikonsumsi / kaloriTarget).toFloat() else 0f

    // NOTE: sesuaikan nama field budget total jika berbeda di UserProfile kamu
    val budgetTotal = foodLogViewModel.userProfile.dailyBudget
    val budgetSisa = foodLogViewModel.remainingBudget
    val budgetTerpakai = budgetTotal - budgetSisa
    val budgetProgress =
        if (budgetTotal.toDouble() > 0) (budgetTerpakai.toDouble() / budgetTotal.toDouble()).toFloat() else 0f

    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AppBottomBar(
                selected = BottomTab.HOME,
                onHomeClick = {},
                onJournalClick = onNavigateToJournal,
                onScanClick = onNavigateToScanner,
                onComingSoon = comingSoon
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeTopBar(onNotificationClick = comingSoon, onSettingsClick = comingSoon)
            GreetingRow(userName = "Feby") // TODO: ambil dari userProfile jika ada field nama
            KaloriCard(
                consumedText = formatNumber(kaloriDikonsumsi),
                targetText = formatNumber(kaloriTarget),
                remainingText = formatNumber((kaloriTarget - kaloriDikonsumsi).coerceAtLeast(0.0)),
                progress = kaloriProgress,
                onClick = onNavigateToJournal
            )
            BudgetCard(
                spentText = formatRupiah(budgetTerpakai),
                totalText = formatRupiah(budgetTotal),
                progress = budgetProgress,
                onClick = comingSoon
            )
            MenuHariIniSection(
                menus = dummyMenus,
                onSeeAll = onNavigateToJournal,
                onCameraClick = { onNavigateToScanner() }
            )
            RekomendasiSection("Jaga asupan protein kamu! Bisa bantu kenyang lebih lama dan menjaga massa otot.")

            // Tombol tes sementara (dari stub lama), hapus jika tidak perlu
            Button(onClick = {
                foodLogViewModel.foodsOfSelectedDate.firstOrNull()?.let { onNavigateToDetail(it.id) }
            }) { Text("Tes: buka detail item pertama") }
        }
    }
}

// ---------- Top bar ----------
@Composable
private fun HomeTopBar(onNotificationClick: () -> Unit, onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Eco, null, tint = GreenPrimary, modifier = Modifier.size(26.dp))
            Spacer(Modifier.width(4.dp))
            Text("Nutri", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = GreenDark)
            Text("Cent", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = GreenPrimary)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onNotificationClick) {
                Box {
                    Icon(Icons.Default.Notifications, "Notifikasi", tint = GreenDark)
                    Box(
                        Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFF4D6D))
                            .align(Alignment.TopEnd)
                    )
                }
            }
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, "Pengaturan", tint = GreenDark)
            }
        }
    }
}

// ---------- Greeting ----------
@Composable
private fun GreetingRow(userName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(GreenTrack),
            contentAlignment = Alignment.Center
        ) {
            Text(userName.take(1).uppercase(), fontWeight = FontWeight.Bold, color = GreenDark, fontSize = 20.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text("Hai, $userName!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GreenDark)
            Text("Yuk, jaga kesehatan dan keuanganmu hari ini!", fontSize = 12.sp, color = TextGray)
        }
    }
}

// ---------- Card dasar ----------
@Composable
private fun SoftCard(
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GreenSoft),
        border = BorderStroke(1.dp, GreenBorder),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.padding(16.dp), content = content)
    }
}

@Composable
private fun CardHeader(title: String, icon: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(GreenTrack),
            contentAlignment = Alignment.Center
        ) { icon() }
        Spacer(Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.SemiBold, color = GreenDark, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = TextGray, modifier = Modifier.size(18.dp))
    }
}

// ---------- Kalori ----------
@Composable
private fun KaloriCard(
    consumedText: String,
    targetText: String,
    remainingText: String,
    progress: Float,
    onClick: () -> Unit
) {
    val p = progress.coerceIn(0f, 1f)
    SoftCard(onClick = onClick) {
        CardHeader("Kalori") {
            Icon(Icons.Default.Eco, null, tint = GreenPrimary, modifier = Modifier.size(14.dp))
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgress(progress = p, size = 88.dp, stroke = 10.dp)
                Spacer(Modifier.height(6.dp))
                Text("${(p * 100).toInt()}% terpenuhi", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GreenDark)
            }
            Spacer(Modifier.width(20.dp))
            Column {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(consumedText, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = GreenDark)
                    Spacer(Modifier.width(4.dp))
                    Text("/ $targetText kcal", fontSize = 14.sp, color = GreenDark, modifier = Modifier.padding(bottom = 6.dp))
                }
                Spacer(Modifier.height(6.dp))
                LegendDot(GreenPrimary, "$consumedText kcal", "dikonsumsi")
                Spacer(Modifier.height(2.dp))
                LegendDot(GreenTrack, "$remainingText kcal", "sisa")
            }
        }
    }
}

@Composable
private fun CircularProgress(progress: Float, size: Dp, stroke: Dp) {
    Canvas(Modifier.size(size)) {
        val strokePx = stroke.toPx()
        val arcSize = Size(this.size.width - strokePx, this.size.height - strokePx)
        val topLeft = Offset(strokePx / 2, strokePx / 2)
        drawArc(GreenTrack, 0f, 360f, false, topLeft, arcSize, style = Stroke(strokePx))
        drawArc(
            GreenPrimary, -90f, 360f * progress, false, topLeft, arcSize,
            style = Stroke(strokePx, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun LegendDot(color: Color, value: String, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(6.dp))
        Text(value, fontSize = 11.sp, color = GreenDark)
        Spacer(Modifier.width(3.dp))
        Text("($label)", fontSize = 11.sp, color = TextGray)
    }
}

// ---------- Budget ----------
@Composable
private fun BudgetCard(spentText: String, totalText: String, progress: Float, onClick: () -> Unit) {
    val p = progress.coerceIn(0f, 1f)
    SoftCard(onClick = onClick) {
        CardHeader("Budget") {
            Text("Rp", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)
        }
        Spacer(Modifier.height(8.dp))
        Text(spentText, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = GreenDark)
        Text("/ $totalText", fontSize = 12.sp, color = TextGray)
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { p },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(50)),
            color = GreenPrimary,
            trackColor = GreenTrack,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )
        Spacer(Modifier.height(6.dp))
        Text("${(p * 100).toInt()}% terpakai", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GreenDark)
    }
}

// ---------- Menu hari ini ----------
@Composable
private fun MenuHariIniSection(
    menus: List<MenuItemUi>,
    onSeeAll: () -> Unit,
    onCameraClick: (MenuItemUi) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(24.dp).clip(CircleShape).background(GreenTrack),
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Default.Restaurant, null, tint = GreenPrimary, modifier = Modifier.size(14.dp)) }
                Spacer(Modifier.width(8.dp))
                Text("Menu Hari Ini", fontWeight = FontWeight.Bold, color = GreenDark, fontSize = 16.sp)
            }
            TextButton(onClick = onSeeAll, contentPadding = PaddingValues(0.dp)) {
                Text("Lihat Semua", fontSize = 12.sp, color = GreenDark)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = GreenDark, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(menus) { menu -> MenuCard(menu) { onCameraClick(menu) } }
        }
    }
}

@Composable
private fun MenuCard(menu: MenuItemUi, onCameraClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(88.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(menu.background)
            .border(1.dp, menu.border, RoundedCornerShape(16.dp))
            .padding(vertical = 10.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier.size(52.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            if (menu.imageRes != null) {
                Image(
                    painter = painterResource(menu.imageRes),
                    contentDescription = menu.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(Icons.Default.Restaurant, null, tint = GreenDark.copy(alpha = 0.6f))
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(menu.title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = GreenDark, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(Modifier.height(4.dp))
        Text(
            "${menu.kcal} kcal",
            fontSize = 10.sp,
            color = GreenDark,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(menu.chipColor)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        )
        Spacer(Modifier.height(8.dp))
        FilledIconButton(
            onClick = onCameraClick,
            modifier = Modifier.size(28.dp),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = GreenPrimary)
        ) {
            Icon(Icons.Default.CameraAlt, "Scan", tint = Color.White, modifier = Modifier.size(14.dp))
        }
    }
}

// ---------- Rekomendasi ----------
@Composable
private fun RekomendasiSection(text: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(24.dp).clip(CircleShape).background(Color(0xFFFFF3C4)),
                contentAlignment = Alignment.Center
            ) { Text("💡", fontSize = 12.sp) }
            Spacer(Modifier.width(8.dp))
            Text("Rekomendasi Hari Ini", fontWeight = FontWeight.Bold, color = GreenDark, fontSize = 16.sp)
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(GreenSoft)
                    .border(1.dp, GreenBorder, RoundedCornerShape(16.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(32.dp).clip(CircleShape).background(GreenTrack),
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Default.Eco, null, tint = GreenPrimary, modifier = Modifier.size(18.dp)) }
                Spacer(Modifier.width(10.dp))
                Text(text, fontSize = 13.sp, color = GreenDark, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
            Spacer(Modifier.width(8.dp))
            CentyMascot()
        }
    }
}

@Composable
private fun CentyMascot() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(96.dp)) {
        Text(
            "Centy here!\nMinum air putih cukup hari ini, ya!",
            fontSize = 8.sp,
            lineHeight = 10.sp,
            color = GreenDark,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(GreenSoft)
                .border(1.dp, GreenBorder, RoundedCornerShape(12.dp))
                .padding(6.dp)
        )
        Spacer(Modifier.height(4.dp))
        // TODO: ganti dengan gambar maskot: Image(painterResource(R.drawable.centy), ...)
        Text("🥑", fontSize = 40.sp)
    }
}

// ---------- Helper ----------
private fun formatNumber(value: Double): String =
    NumberFormat.getNumberInstance(Locale.US).apply { maximumFractionDigits = 0 }.format(value)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    NutriCentTheme {
        HomeScreen(
            foodLogViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
            onNavigateToJournal = {},
            onNavigateToScanner = {},
            onNavigateToDetail = {}
        )
    }
}