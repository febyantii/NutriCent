
package com.nutricent.app.ui.screens.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutricent.app.R
import com.nutricent.app.data.DummyData
import com.nutricent.app.model.FoodLog
import com.nutricent.app.model.MealType
import com.nutricent.app.ui.components.AppBottomBar
import com.nutricent.app.ui.components.BottomTab
import com.nutricent.app.ui.theme.BreakfastBackground
import com.nutricent.app.ui.theme.BreakfastBorder
import com.nutricent.app.ui.theme.BreakfastChip
import com.nutricent.app.ui.theme.DinnerBackground
import com.nutricent.app.ui.theme.DinnerBorder
import com.nutricent.app.ui.theme.DinnerChip
import com.nutricent.app.ui.theme.HomeGreenSoft
import com.nutricent.app.ui.theme.HomeGreenTrack
import com.nutricent.app.ui.theme.HomeNotificationRed
import com.nutricent.app.ui.theme.HomeRecommendationYellow
import com.nutricent.app.ui.theme.HomeTextSecondary
import com.nutricent.app.ui.theme.LunchBackground
import com.nutricent.app.ui.theme.LunchBorder
import com.nutricent.app.ui.theme.LunchChip
import com.nutricent.app.ui.theme.NutriBorderColor
import com.nutricent.app.ui.theme.NutriButtonGreen
import com.nutricent.app.ui.theme.NutriCentTheme
import com.nutricent.app.ui.theme.NutriDarkGreen
import com.nutricent.app.ui.theme.NutriScreenBackground
import com.nutricent.app.ui.theme.SnackBackground
import com.nutricent.app.ui.theme.SnackBorder
import com.nutricent.app.ui.theme.SnackChip
import com.nutricent.app.util.formatRupiah
import com.nutricent.app.viewmodel.FoodLogViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

// ---------- Model UI untuk kartu Menu Hari Ini ----------

data class MenuItemUi(
    val title: String,
    val foodName: String,
    val kcal: Int,
    val background: Color,
    val border: Color,
    val chipColor: Color,
    @param:DrawableRes val imageRes: Int? = null
)

/**
 * Mengubah data makanan menjadi tampilan kartu sesuai jenis makanan.
 * Seluruh warna kartu berasal dari Color.kt.
 */
private fun FoodLog.toMenuUi(): MenuItemUi = when (mealType) {
    MealType.BREAKFAST -> MenuItemUi(
        title = "Breakfast",
        foodName = name,
        kcal = calories,
        background = BreakfastBackground,
        border = BreakfastBorder,
        chipColor = BreakfastChip,
        imageRes = imageRes
    )

    MealType.LUNCH -> MenuItemUi(
        title = "Lunch",
        foodName = name,
        kcal = calories,
        background = LunchBackground,
        border = LunchBorder,
        chipColor = LunchChip,
        imageRes = imageRes
    )

    MealType.DINNER -> MenuItemUi(
        title = "Dinner",
        foodName = name,
        kcal = calories,
        background = DinnerBackground,
        border = DinnerBorder,
        chipColor = DinnerChip,
        imageRes = imageRes
    )

    else -> MenuItemUi(
        title = "Snack",
        foodName = name,
        kcal = calories,
        background = SnackBackground,
        border = SnackBorder,
        chipColor = SnackChip,
        imageRes = imageRes
    )
}

// ---------- Home Screen ----------

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
        scope.launch {
            snackbarHostState.showSnackbar("Segera hadir")
        }
    }

    // ---------- Kalori ----------

    val kaloriDikonsumsi =
        foodLogViewModel.totalCalories.toDouble()

    val kaloriTarget =
        foodLogViewModel.userProfile.dailyCalorieTarget.toDouble()

    val kaloriProgress =
        if (kaloriTarget > 0) {
            (kaloriDikonsumsi / kaloriTarget).toFloat()
        } else {
            0f
        }

    // ---------- Budget ----------

    val budgetTotal = foodLogViewModel.userProfile.dailyBudget
    val budgetSisa = foodLogViewModel.remainingBudget
    val budgetTerpakai = budgetTotal - budgetSisa

    val budgetProgress =
        if (budgetTotal.toDouble() > 0) {
            (budgetTerpakai.toDouble() / budgetTotal.toDouble()).toFloat()
        } else {
            0f
        }

    Scaffold(
        containerColor = NutriScreenBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            // Menggunakan navbar bersama dari AppBottomBar.kt
            AppBottomBar(
                selected = BottomTab.HOME,
                onHomeClick = {},
                onJournalClick = onNavigateToJournal,
                onScanClick = onNavigateToScanner,
                onComingSoon = comingSoon
            )
        }
    ) { padding ->
        HomeContent(
            modifier = Modifier.padding(padding),
            userName = foodLogViewModel.userProfile.name,
            consumedText = formatNumber(kaloriDikonsumsi),
            targetText = formatNumber(kaloriTarget),
            remainingText = formatNumber(
                (kaloriTarget - kaloriDikonsumsi).coerceAtLeast(0.0)
            ),
            kaloriProgress = kaloriProgress,
            spentText = formatRupiah(budgetTerpakai),
            totalText = formatRupiah(budgetTotal),
            budgetProgress = budgetProgress,
            menus = foodLogViewModel.foodsOfSelectedDate.map {
                it.toMenuUi()
            },
            onKaloriClick = onNavigateToJournal,
            onBudgetClick = comingSoon,
            onNotificationClick = comingSoon,
            onSettingsClick = comingSoon,
            onSeeAll = onNavigateToJournal,
            onCameraClick = {
                onNavigateToScanner()
            },
            onTestDetailClick = {
                foodLogViewModel.foodsOfSelectedDate
                    .firstOrNull()
                    ?.let { onNavigateToDetail(it.id) }
            }
        )
    }
}

// ---------- Isi layar ----------

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    userName: String,
    consumedText: String,
    targetText: String,
    remainingText: String,
    kaloriProgress: Float,
    spentText: String,
    totalText: String,
    budgetProgress: Float,
    menus: List<MenuItemUi>,
    onKaloriClick: () -> Unit,
    onBudgetClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onSeeAll: () -> Unit,
    onCameraClick: (MenuItemUi) -> Unit,
    onTestDetailClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeTopBar(
            onNotificationClick = onNotificationClick,
            onSettingsClick = onSettingsClick
        )

        GreetingRow(userName = userName)

        KaloriCard(
            consumedText = consumedText,
            targetText = targetText,
            remainingText = remainingText,
            progress = kaloriProgress,
            onClick = onKaloriClick
        )

        BudgetCard(
            spentText = spentText,
            totalText = totalText,
            progress = budgetProgress,
            onClick = onBudgetClick
        )

        MenuHariIniSection(
            menus = menus,
            onSeeAll = onSeeAll,
            onCameraClick = onCameraClick
        )

        RekomendasiSection(
            text = "Jaga asupan protein kamu! Bisa bantu kenyang lebih lama dan menjaga massa otot."
        )
    }
}

// ---------- Top bar ----------

@Composable
private fun HomeTopBar(
    onNotificationClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.logo_nutricent),
            contentDescription = "Logo NutriCent",
            contentScale = ContentScale.Fit,
            alignment = Alignment.CenterStart,
            modifier = Modifier
                .height(65.dp)
                .widthIn(max = 150.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onNotificationClick) {
                Box {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = NutriDarkGreen
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(HomeNotificationRed)
                            .align(Alignment.TopEnd)
                    )
                }
            }

            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Pengaturan",
                    tint = NutriDarkGreen
                )
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
                .background(HomeGreenTrack),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.take(1).uppercase(),
                fontWeight = FontWeight.Bold,
                color = NutriDarkGreen,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = "Hai, $userName!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = NutriDarkGreen
            )

            Text(
                text = "Yuk, jaga kesehatan dan keuanganmu hari ini!",
                fontSize = 12.sp,
                color = HomeTextSecondary
            )
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
        colors = CardDefaults.cardColors(
            containerColor = HomeGreenSoft
        ),
        border = BorderStroke(1.dp, NutriBorderColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
private fun CardHeader(
    title: String,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(HomeGreenTrack),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Spacer(Modifier.width(8.dp))

        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = NutriDarkGreen,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = HomeTextSecondary,
            modifier = Modifier.size(18.dp)
        )
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
        CardHeader(title = "Kalori") {
            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = null,
                tint = NutriButtonGreen,
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgress(
                    progress = p,
                    size = 88.dp,
                    stroke = 10.dp
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "${(p * 100).toInt()}% terpenuhi",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NutriDarkGreen
                )
            }

            Spacer(Modifier.width(20.dp))

            Column {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = consumedText,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = NutriDarkGreen
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = "/ $targetText kcal",
                        fontSize = 14.sp,
                        color = NutriDarkGreen,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(Modifier.height(6.dp))

                LegendDot(
                    color = NutriButtonGreen,
                    value = "$consumedText kcal",
                    label = "dikonsumsi"
                )

                Spacer(Modifier.height(2.dp))

                LegendDot(
                    color = HomeGreenTrack,
                    value = "$remainingText kcal",
                    label = "sisa"
                )
            }
        }
    }
}

@Composable
private fun CircularProgress(
    progress: Float,
    size: Dp,
    stroke: Dp
) {
    Canvas(Modifier.size(size)) {
        val strokePx = stroke.toPx()
        val arcSize = Size(
            width = this.size.width - strokePx,
            height = this.size.height - strokePx
        )
        val topLeft = Offset(strokePx / 2, strokePx / 2)

        drawArc(
            color = HomeGreenTrack,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(strokePx)
        )

        drawArc(
            color = NutriButtonGreen,
            startAngle = -90f,
            sweepAngle = 360f * progress.coerceIn(0f, 1f),
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(
                width = strokePx,
                cap = StrokeCap.Round
            )
        )
    }
}

@Composable
private fun LegendDot(
    color: Color,
    value: String,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text = value,
            fontSize = 11.sp,
            color = NutriDarkGreen
        )

        Spacer(Modifier.width(3.dp))

        Text(
            text = "($label)",
            fontSize = 11.sp,
            color = HomeTextSecondary
        )
    }
}

// ---------- Budget ----------

@Composable
private fun BudgetCard(
    spentText: String,
    totalText: String,
    progress: Float,
    onClick: () -> Unit
) {
    val p = progress.coerceIn(0f, 1f)

    SoftCard(onClick = onClick) {
        CardHeader(title = "Budget") {
            Text(
                text = "Rp",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = NutriButtonGreen
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = spentText,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = NutriDarkGreen
        )

        Text(
            text = "/ $totalText",
            fontSize = 12.sp,
            color = HomeTextSecondary
        )

        Spacer(Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { p },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(50)),
            color = NutriButtonGreen,
            trackColor = HomeGreenTrack,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "${(p * 100).toInt()}% terpakai",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = NutriDarkGreen
        )
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
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(HomeGreenTrack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = null,
                        tint = NutriButtonGreen,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "Menu Hari Ini",
                    fontWeight = FontWeight.Bold,
                    color = NutriDarkGreen,
                    fontSize = 16.sp
                )
            }

            TextButton(
                onClick = onSeeAll,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Lihat Semua",
                    fontSize = 12.sp,
                    color = NutriDarkGreen
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = NutriDarkGreen,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(menus) { menu ->
                MenuCard(menu = menu) {
                    onCameraClick(menu)
                }
            }
        }
    }
}

@Composable
private fun MenuCard(
    menu: MenuItemUi,
    onCameraClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(menu.background)
            .border(
                width = 1.dp,
                color = menu.border,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                vertical = 10.dp,
                horizontal = 6.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            if (menu.imageRes != null) {
                Image(
                    painter = painterResource(menu.imageRes),
                    contentDescription = menu.foodName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = null,
                    tint = NutriDarkGreen.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = menu.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = NutriDarkGreen,
            maxLines = 1
        )

        Text(
            text = menu.foodName,
            fontSize = 10.sp,
            color = HomeTextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "${menu.kcal} kcal",
            fontSize = 10.sp,
            color = NutriDarkGreen,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(menu.chipColor)
                .padding(
                    horizontal = 8.dp,
                    vertical = 2.dp
                )
        )

        Spacer(Modifier.height(8.dp))

        FilledIconButton(
            onClick = onCameraClick,
            modifier = Modifier.size(28.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = NutriButtonGreen
            )
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Scan makanan",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

// ---------- Rekomendasi ----------

@Composable
private fun RekomendasiSection(text: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(HomeRecommendationYellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "💡",
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Rekomendasi Hari Ini",
                fontWeight = FontWeight.Bold,
                color = NutriDarkGreen,
                fontSize = 16.sp
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(HomeGreenSoft)
                    .border(
                        width = 1.dp,
                        color = NutriBorderColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(HomeGreenTrack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = NutriButtonGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(Modifier.width(10.dp))

                Text(
                    text = text,
                    fontSize = 13.sp,
                    color = NutriDarkGreen,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.width(8.dp))

            CentyMascot()
        }
    }
}

@Composable
private fun CentyMascot() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(96.dp)
    ) {
        Text(
            text = "Centy here!\nMinum air putih cukup hari ini, ya!",
            fontSize = 8.sp,
            lineHeight = 10.sp,
            color = NutriDarkGreen,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(HomeGreenSoft)
                .border(
                    width = 1.dp,
                    color = NutriBorderColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(6.dp)
        )

        Spacer(Modifier.height(4.dp))

        Image(
            painter = painterResource(R.drawable.maskot_nutricent),
            contentDescription = "Centy",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(72.dp)
        )
    }
}

// ---------- Helper ----------

private fun formatNumber(value: Double): String =
    NumberFormat.getNumberInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }.format(value)

// ---------- Preview ----------

@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 844,
    name = "NutriCent Home Content"
)
@Composable
private fun HomeContentPreview() {
    NutriCentTheme {
        HomeContent(
            userName = "Feby",
            consumedText = "1,100",
            targetText = "1,500",
            remainingText = "400",
            kaloriProgress = 0.73f,
            spentText = "Rp26.000",
            totalText = "Rp40.000",
            budgetProgress = 0.65f,
            menus = DummyData.foodLogs
                .take(4)
                .map { it.toMenuUi() },
            onKaloriClick = {},
            onBudgetClick = {},
            onNotificationClick = {},
            onSettingsClick = {},
            onSeeAll = {},
            onCameraClick = {},
            onTestDetailClick = {}
        )
    }
}
