
package com.nutricent.app.ui.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutricent.app.model.MealType
import com.nutricent.app.ui.theme.NutriButtonGreen
import com.nutricent.app.ui.theme.NutriCentTheme
import com.nutricent.app.ui.theme.NutriDarkGreen
import com.nutricent.app.util.Validators
import com.nutricent.app.viewmodel.FoodLogViewModel
import java.time.LocalDate

@Composable
fun DetailScreen(
    foodId: Int,
    foodLogViewModel: FoodLogViewModel,
    onBack: () -> Unit
) {
    val food = remember(foodId) {
        foodLogViewModel.getFoodById(foodId)
    }
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (food == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data makanan tidak ditemukan",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            var name by remember(foodId) {
                mutableStateOf(food.name)
            }
            var notes by remember(foodId) {
                mutableStateOf(food.notes)
            }
            var priceText by remember(foodId) {
                mutableStateOf(food.price.toString())
            }
            var selectedMealType by remember(foodId) {
                mutableStateOf(food.mealType)
            }

            var isNameFocused by remember {
                mutableStateOf(false)
            }
            var isPriceFocused by remember {
                mutableStateOf(false)
            }

            var nameError by remember {
                mutableStateOf<String?>(null)
            }
            var notesError by remember {
                mutableStateOf<String?>(null)
            }
            var priceError by remember {
                mutableStateOf<String?>(null)
            }

            val parsedGram = remember(notes) {
                val regex = Regex(
                    """(\d+)\s*(g|gram)""",
                    RegexOption.IGNORE_CASE
                )
                val match = regex.find(notes)
                match?.groupValues?.get(1)?.toIntOrNull()
            }

            val baseCalories = food.calories

            val calculatedCalories by remember(
                notes,
                baseCalories
            ) {
                derivedStateOf {
                    if (parsedGram != null && parsedGram > 0) {
                        (
                                (baseCalories.toDouble() / 100.0) *
                                        parsedGram
                                ).toInt()
                    } else {
                        baseCalories
                    }
                }
            }

            val calculatedCarbs by remember(
                parsedGram,
                food.carbs
            ) {
                derivedStateOf {
                    if (parsedGram != null && parsedGram > 0) {
                        (
                                (food.carbs.toDouble() / 100.0) *
                                        parsedGram
                                ).toInt()
                    } else {
                        food.carbs
                    }
                }
            }

            val calculatedProtein by remember(
                parsedGram,
                food.protein
            ) {
                derivedStateOf {
                    if (parsedGram != null && parsedGram > 0) {
                        (
                                (food.protein.toDouble() / 100.0) *
                                        parsedGram
                                ).toInt()
                    } else {
                        food.protein
                    }
                }
            }

            val calculatedFat by remember(
                parsedGram,
                food.fat
            ) {
                derivedStateOf {
                    if (parsedGram != null && parsedGram > 0) {
                        (
                                (food.fat.toDouble() / 100.0) *
                                        parsedGram
                                ).toInt()
                    } else {
                        food.fat
                    }
                }
            }

            // Perubahan: imePadding agar konten menyesuaikan keyboard.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .imePadding()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 12.dp
                        )
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                onBack()
                            }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector =
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali",
                                tint = NutriDarkGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Text(
                        text = "Detail Makanan",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = NutriDarkGreen,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(
                            horizontal = 18.dp,
                            vertical = 6.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = food.imageRes),
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.large)
                    )

                    CardSection {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector =
                                            Icons.Default.Restaurant,
                                        contentDescription = null,
                                        tint = NutriDarkGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                BasicTextField(
                                    value = name,
                                    onValueChange = {
                                        name = it
                                        nameError =
                                            Validators.validateRequired(
                                                it,
                                                "Nama makanan"
                                            )
                                    },
                                    textStyle =
                                        MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = NutriDarkGreen
                                        ),
                                    cursorBrush =
                                        SolidColor(NutriDarkGreen),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .onFocusChanged {
                                            isNameFocused = it.isFocused
                                        }
                                )

                                if (nameError != null) {
                                    Text(
                                        text = nameError!!,
                                        style =
                                            MaterialTheme.typography.bodySmall,
                                        color =
                                            MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            AnimatedVisibility(
                                visible = !isNameFocused,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color =
                                        MaterialTheme.colorScheme
                                            .primaryContainer
                                            .copy(alpha = 0.5f),
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector =
                                                Icons.Default.Edit,
                                            contentDescription = "Edit Nama",
                                            tint = NutriDarkGreen,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    CardSection {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector =
                                        Icons.Default.Description,
                                    contentDescription = null,
                                    tint = NutriDarkGreen,
                                    modifier = Modifier.size(18.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "Catatan (Opsional)",
                                    style =
                                        MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                    color = NutriDarkGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color =
                                            MaterialTheme.colorScheme.surface,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .border(
                                        width = 1.dp,
                                        color =
                                            MaterialTheme.colorScheme
                                                .outlineVariant,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .padding(12.dp)
                            ) {
                                if (notes.isEmpty()) {
                                    Text(
                                        text = "Tambah catatan (misal: 150g, saus mentega, 1/2 dada ayam)",
                                        style =
                                            MaterialTheme.typography.bodyMedium.copy(
                                                fontSize = 13.sp
                                            ),
                                        color =
                                            NutriDarkGreen.copy(alpha = 0.4f)
                                    )
                                }

                                BasicTextField(
                                    value = notes,
                                    onValueChange = {
                                        notes = it
                                        notesError =
                                            Validators.validateNotes(it)
                                    },
                                    textStyle =
                                        MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 13.sp,
                                            color = NutriDarkGreen
                                        ),
                                    cursorBrush =
                                        SolidColor(NutriDarkGreen),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            if (notesError != null) {
                                Text(
                                    text = notesError!!,
                                    style =
                                        MaterialTheme.typography.bodySmall,
                                    color =
                                        MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    CardSection {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween,
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector =
                                            Icons.Default.WbSunny,
                                        contentDescription = null,
                                        tint = NutriDarkGreen,
                                        modifier = Modifier.size(18.dp)
                                    )

                                    Spacer(
                                        modifier = Modifier.width(8.dp)
                                    )

                                    Text(
                                        text = "Kategori Makan",
                                        style =
                                            MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                        color = NutriDarkGreen
                                    )
                                }

                                Icon(
                                    imageVector =
                                        Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint =
                                        NutriDarkGreen.copy(alpha = 0.5f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.spacedBy(6.dp)
                            ) {
                                MealType.entries.forEach { mealType ->
                                    val isSelected =
                                        selectedMealType == mealType

                                    CustomMealChip(
                                        label = mealType.name
                                            .lowercase()
                                            .replaceFirstChar {
                                                it.uppercase()
                                            },
                                        isSelected = isSelected,
                                        icon = getMealIcon(mealType),
                                        onClick = {
                                            selectedMealType = mealType
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    CardSection {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color =
                                        MaterialTheme.colorScheme
                                            .primaryContainer,
                                    modifier = Modifier.size(22.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector =
                                                Icons.Default.AddCircle,
                                            contentDescription = null,
                                            tint = NutriDarkGreen,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "Prediksi Nutrisi (AI)",
                                    style =
                                        MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                    color = NutriDarkGreen
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint =
                                        NutriDarkGreen.copy(alpha = 0.4f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween,
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Text(
                                        text = "$calculatedCalories",
                                        style =
                                            MaterialTheme.typography.headlineLarge.copy(
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                        color = NutriDarkGreen
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "kcal",
                                        style =
                                            MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                        color = NutriDarkGreen,
                                        modifier =
                                            Modifier.padding(bottom = 4.dp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(1.dp)
                                        .background(
                                            MaterialTheme.colorScheme
                                                .outlineVariant
                                        )
                                )

                                Row(
                                    horizontalArrangement =
                                        Arrangement.spacedBy(6.dp)
                                ) {
                                    NutritonBadge(
                                        label = "Carbs",
                                        value = "${calculatedCarbs}g",
                                        icon = "🌿"
                                    )
                                    NutritonBadge(
                                        label = "Protein",
                                        value = "${calculatedProtein}g",
                                        icon = "↖"
                                    )
                                    NutritonBadge(
                                        label = "Fat",
                                        value = "${calculatedFat}g",
                                        icon = "💧"
                                    )
                                }
                            }
                        }
                    }

                    CardSection {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector =
                                        Icons.Default.Payments,
                                    contentDescription = null,
                                    tint = NutriDarkGreen,
                                    modifier = Modifier.size(18.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "Estimasi Harga",
                                    style =
                                        MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                    color = NutriDarkGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color =
                                            MaterialTheme.colorScheme.surface,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (priceError != null) {
                                            MaterialTheme.colorScheme.error
                                        } else {
                                            MaterialTheme.colorScheme
                                                .outlineVariant
                                        },
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .padding(
                                        horizontal = 14.dp,
                                        vertical = 12.dp
                                    )
                            ) {
                                Text(
                                    text = "Rp",
                                    style =
                                        MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                    color = NutriDarkGreen
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                BasicTextField(
                                    value = priceText,
                                    onValueChange = { input ->
                                        val filtered =
                                            Validators.filterDigits(input)
                                        priceText = filtered
                                        priceError =
                                            Validators.validatePrice(filtered)
                                    },
                                    textStyle =
                                        MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = NutriDarkGreen
                                        ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    cursorBrush =
                                        SolidColor(NutriDarkGreen),
                                    modifier = Modifier
                                        .weight(1f)
                                        .onFocusChanged {
                                            isPriceFocused = it.isFocused
                                        }
                                )

                                AnimatedVisibility(
                                    visible = !isPriceFocused,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Icon(
                                        imageVector =
                                            Icons.Default.Edit,
                                        contentDescription = "Edit Harga",
                                        tint =
                                            NutriDarkGreen.copy(alpha = 0.5f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            if (priceError != null) {
                                Text(
                                    text = priceError!!,
                                    style =
                                        MaterialTheme.typography.bodySmall,
                                    color =
                                        MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val isFormValid =
                        nameError == null &&
                                notesError == null &&
                                priceError == null &&
                                name.isNotBlank() &&
                                priceText.isNotBlank()

                    Button(
                        onClick = {
                            if (isFormValid) {
                                val updatedFood = food.copy(
                                    name = name.trim(),
                                    notes = notes.trim(),
                                    mealType = selectedMealType,
                                    calories = calculatedCalories,
                                    carbs = calculatedCarbs,
                                    protein = calculatedProtein,
                                    fat = calculatedFat,
                                    price = priceText.toIntOrNull() ?: 0,
                                    date = LocalDate.now()
                                )
                                foodLogViewModel.saveFood(updatedFood)
                                onBack()
                            }
                        },
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NutriButtonGreen,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            text = "Save to Daily Log",
                            style =
                                MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun CardSection(
    content: @Composable () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Box(
            modifier = Modifier.padding(14.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun CustomMealChip(
    label: String,
    isSelected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isSelected) {
                    NutriDarkGreen
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                },
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 2.dp
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NutriDarkGreen,
                modifier = Modifier.size(12.dp)
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Medium
                    },
                    fontSize = 11.sp
                ),
                color = NutriDarkGreen
            )
        }
    }
}

private fun getMealIcon(
    mealType: MealType
): ImageVector {
    return when (mealType) {
        MealType.BREAKFAST -> Icons.Default.WbSunny
        MealType.LUNCH -> Icons.Default.WbSunny
        MealType.DINNER -> Icons.Default.NightlightRound
        MealType.SNACK -> Icons.Default.AddReaction
    }
}

@Composable
private fun NutritonBadge(
    label: String,
    value: String,
    icon: String
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 6.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.width(3.dp))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp
                    ),
                    color = NutriDarkGreen
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = NutriDarkGreen
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    NutriCentTheme {
        DetailScreen(
            foodId = 1,
            foodLogViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel(),
            onBack = {}
        )
    }
}
