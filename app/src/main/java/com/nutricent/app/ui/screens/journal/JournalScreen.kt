package com.nutricent.app.ui.screens.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutricent.app.R
import com.nutricent.app.model.FoodLog
import com.nutricent.app.model.MealType
import com.nutricent.app.ui.components.AppBottomBar
import com.nutricent.app.ui.components.BottomTab
import com.nutricent.app.viewmodel.FoodLogViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

private val JournalGreen = Color(0xFF299D75)
private val JournalDarkGreen = Color(0xFF145448)
private val JournalMint = Color(0xFFE6F5EC)
private val JournalBorder = Color(0xFFD4EBE0)
private val JournalText = Color(0xFF263D35)
private val JournalGray = Color(0xFF879990)

/** Journal screen intended to replace the repository's Journal placeholder. */
@Composable
fun JournalScreen(
    foodLogViewModel: FoodLogViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf<FoodLog?>(null) }

    val selectedDate = foodLogViewModel.selectedDate
    val foods = foodLogViewModel.foodsOfSelectedDate
    val totalCalories = foodLogViewModel.totalCalories
    val totalExpense = foodLogViewModel.totalSpent
    val calorieTarget = foodLogViewModel.userProfile.dailyCalorieTarget

    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AppBottomBar(
                selected = BottomTab.JOURNAL,
                onHomeClick = onNavigateToHome,
                onJournalClick = {},
                onScanClick = onNavigateToScanner,
                onComingSoon = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Segera hadir")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 22.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { JournalHeader() }

            item {
                JournalDateSelector(
                    selectedDate = selectedDate,
                    onDateSelected = foodLogViewModel::selectDate
                )
            }

            if (foods.isEmpty()) {
                item { JournalEmptyState() }
            } else {
                MealType.values().forEach { mealType ->
                    val meals = foodLogViewModel.foodsOf(mealType)
                    if (meals.isNotEmpty()) {
                        items(
                            items = meals,
                            key = { food -> "${mealType.name}_${food.id}" }
                        ) { food ->
                            JournalFoodCard(
                                food = food,
                                onManage = { selectedFood = food }
                            )
                        }
                    }
                }
            }

            item {
                JournalSummary(
                    calories = totalCalories,
                    calorieTarget = calorieTarget,
                    expense = totalExpense,
                    onAddManual = { showAddDialog = true }
                )
            }
        }
    }

    if (showAddDialog) {
        JournalAddFoodDialog(
            onDismiss = { showAddDialog = false },
            onSave = { mealType, name, calories, price ->
                foodLogViewModel.saveFood(
                    FoodLog(
                        id = -1,
                        name = name,
                        mealType = mealType,
                        calories = calories,
                        carbs = 0,
                        protein = 0,
                        fat = 0,
                        price = price,
                        notes = "",
                        date = selectedDate,
                        imageRes = R.drawable.ic_launcher_foreground,
                        isHomemade = false
                    )
                )
                showAddDialog = false
            }
        )
    }

    selectedFood?.let { food ->
        AlertDialog(
            onDismissRequest = { selectedFood = null },
            title = { Text("Kelola Log", color = JournalDarkGreen) },
            text = { Text("Pilih tindakan untuk ${food.name}.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedFood = null
                        onNavigateToDetail(food.id)
                    }
                ) {
                    Text("Lihat Detail", color = JournalGreen)
                }
            },
            dismissButton = {
                Row {
                    TextButton(
                        onClick = {
                            foodLogViewModel.deleteFood(food.id)
                            selectedFood = null
                            scope.launch {
                                snackbarHostState.showSnackbar("Log makanan dihapus")
                            }
                        }
                    ) {
                        Text("Hapus", color = Color(0xFFC13D45))
                    }
                    TextButton(onClick = { selectedFood = null }) {
                        Text("Batal", color = JournalGray)
                    }
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
private fun JournalHeader() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🌿", fontSize = 25.sp)
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "NutriCent",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = JournalDarkGreen
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("♧", color = JournalDarkGreen, fontSize = 24.sp)
                Spacer(Modifier.width(18.dp))
                Text("⚙", color = JournalText, fontSize = 22.sp)
            }
        }

        Spacer(Modifier.height(22.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Jurnal Harian",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                color = JournalDarkGreen,
                letterSpacing = (-0.7).sp
            )
            Spacer(Modifier.width(6.dp))
            Text("⌁", fontSize = 19.sp, color = JournalGreen)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Lacak asupan makanan dan pengeluaranmu setiap hari!",
            fontSize = 12.sp,
            color = Color(0xFF71847B)
        )
    }
}

@Composable
private fun JournalDateSelector(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val weekStart = selectedDate.minusDays((selectedDate.dayOfWeek.value - 1).toLong())
    val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(27.dp)
                .background(JournalMint, CircleShape)
                .clickable { onDateSelected(selectedDate.minusWeeks(1)) },
            contentAlignment = Alignment.Center
        ) {
            Text("‹", color = JournalGreen, fontSize = 22.sp)
        }

        days.forEachIndexed { index, day ->
            val date = weekStart.plusDays(index.toLong())
            val chosen = date == selectedDate
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        if (chosen) JournalDarkGreen else Color(0xFFF0F6F2),
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { onDateSelected(date) }
                    .padding(vertical = 9.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day,
                    fontSize = 10.sp,
                    color = if (chosen) Color.White else JournalGray
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = date.dayOfMonth.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (chosen) Color.White else JournalText
                )
                Spacer(Modifier.height(3.dp))
                Box(
                    Modifier
                        .size(4.dp)
                        .background(
                            if (chosen) Color.White else Color.Transparent,
                            CircleShape
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .size(27.dp)
                .background(JournalMint, CircleShape)
                .clickable { onDateSelected(selectedDate.plusWeeks(1)) },
            contentAlignment = Alignment.Center
        ) {
            Text("›", color = JournalGreen, fontSize = 22.sp)
        }
    }
}

@Composable
private fun JournalFoodCard(
    food: FoodLog,
    onManage: () -> Unit
) {
    var expanded by remember(food.id) { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, JournalBorder, RoundedCornerShape(17.dp)),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mealIcon(food.mealType),
                    fontSize = 17.sp,
                    color = JournalGreen
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = displayMealName(food.mealType),
                    modifier = Modifier.weight(1f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = JournalText
                )
                Text("${food.calories} kcal", fontSize = 10.sp, color = JournalDarkGreen)
                Text(" • ", fontSize = 10.sp, color = JournalBorder)
                Text(formatJournalRupiah(food.price), fontSize = 10.sp, color = JournalDarkGreen)
                Spacer(Modifier.width(5.dp))
                Text(if (expanded) "⌃" else "⌄", fontSize = 15.sp, color = JournalGray)
            }

            if (expanded) {
                Spacer(Modifier.height(11.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = food.imageRes),
                        contentDescription = food.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(65.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(JournalMint)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = food.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = JournalText
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = food.notes.ifBlank { "Catatan konsumsi harian" },
                            fontSize = 10.sp,
                            color = JournalGray
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            JournalFoodTag("${food.calories} kcal")
                            JournalFoodTag(formatJournalRupiah(food.price))
                        }
                    }
                    Text(
                        text = "⋮",
                        modifier = Modifier.clickable { onManage() },
                        fontSize = 22.sp,
                        color = Color(0xFF8DA4BD)
                    )
                }
            }
        }
    }
}

@Composable
private fun JournalFoodTag(text: String) {
    Text(
        text = "◆  $text",
        modifier = Modifier
            .background(JournalMint, RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 5.dp),
        fontSize = 9.sp,
        color = JournalDarkGreen
    )
}

@Composable
private fun JournalSummary(
    calories: Int,
    calorieTarget: Int,
    expense: Int,
    onAddManual: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, JournalBorder, RoundedCornerShape(17.dp))
            .background(Color.White, RoundedCornerShape(17.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("♨", color = JournalGreen, fontSize = 21.sp)
                    Spacer(Modifier.width(8.dp))
                    Text("Total Kalori", fontSize = 12.sp, color = JournalGray)
                }
                Spacer(Modifier.height(7.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        formatJournalNumber(calories),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = JournalDarkGreen
                    )
                    Text(
                        " /${formatJournalNumber(calorieTarget)} kcal",
                        fontSize = 12.sp,
                        color = JournalGray
                    )
                }
                Spacer(Modifier.height(10.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(7.dp)
                        .background(JournalMint, CircleShape)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(
                                (calories.toFloat() / calorieTarget.coerceAtLeast(1))
                                    .coerceIn(0f, 1f)
                            )
                            .fillMaxHeight()
                            .background(JournalGreen, CircleShape)
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Box(
                Modifier
                    .width(1.dp)
                    .height(72.dp)
                    .background(JournalBorder)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(0.85f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("▣", color = JournalGreen, fontSize = 20.sp)
                    Spacer(Modifier.width(7.dp))
                    Text("Total Pengeluaran", fontSize = 11.sp, color = JournalGray)
                }
                Spacer(Modifier.height(9.dp))
                Text(
                    formatJournalRupiah(expense),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = JournalDarkGreen
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        OutlinedButton(
            onClick = onAddManual,
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, JournalGreen),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = JournalDarkGreen),
            contentPadding = PaddingValues(8.dp)
        ) {
            Text("+  Add Manual", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun JournalAddFoodDialog(
    onDismiss: () -> Unit,
    onSave: (MealType, String, Int, Int) -> Unit
) {
    var category by remember { mutableStateOf("Camilan") }
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Tambah Log", color = JournalDarkGreen, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Kategori: Sarapan/Makan Siang/Makan Malam/Camilan") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama makanan") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it.filter(Char::isDigit) },
                    label = { Text("Kalori (kcal)") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it.filter(Char::isDigit) },
                    label = { Text("Harga (Rupiah)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onSave(
                            parseMealType(category),
                            name.trim(),
                            calories.toIntOrNull() ?: 0,
                            price.toIntOrNull() ?: 0
                        )
                    }
                }
            ) {
                Text("Simpan", color = JournalGreen)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = JournalGray)
            }
        },
        containerColor = Color.White
    )
}

@Composable
private fun JournalEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🍃", fontSize = 38.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            "Belum ada catatan makanan",
            color = JournalDarkGreen,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Tambah log untuk tanggal yang dipilih.",
            fontSize = 12.sp,
            color = JournalGray
        )
    }
}

private fun displayMealName(mealType: MealType): String {
    return if (mealType == MealType.SNACK) "Camilan" else mealType.label
}

private fun mealIcon(mealType: MealType): String {
    return when (mealType) {
        MealType.BREAKFAST -> "☼"
        MealType.LUNCH -> "♜"
        MealType.DINNER -> "◕"
        MealType.SNACK -> "▣"
    }
}

private fun parseMealType(value: String): MealType {
    return when (value.trim().lowercase()) {
        "sarapan" -> MealType.BREAKFAST
        "makan siang" -> MealType.LUNCH
        "makan malam" -> MealType.DINNER
        "cemilan", "camilan", "snack" -> MealType.SNACK
        else -> MealType.SNACK
    }
}

private fun formatJournalRupiah(amount: Int): String {
    return "Rp${formatJournalNumber(amount)}"
}

private fun formatJournalNumber(number: Int): String {
    return number.toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}
