package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            ),

            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )

        setContent {
            UnitConverterTheme {
                UnitConverterApp()
            }
        }
    }
}

@Composable
fun UnitConverterApp() {

    var selectedCategory by rememberSaveable {
        mutableStateOf("Length")
    }

    var inputValue by rememberSaveable {
        mutableStateOf("")
    }

    var fromUnit by rememberSaveable {
        mutableStateOf("Meters")
    }

    var toUnit by rememberSaveable {
        mutableStateOf("Kilometers")
    }

    val result = convertValue(
        value = inputValue, category = selectedCategory, fromUnit = fromUnit, toUnit = toUnit
    )

    val availableUnits = when (selectedCategory) {
        "Length" -> listOf(
            "Millimeters", "Centimeters", "Meters", "Kilometers", "Miles", "Feet", "Inches"
        )

        "Weight" -> listOf(
            "Milligrams", "Grams", "Kilograms", "Pounds", "Ounces"
        )

        "Temperature" -> listOf(
            "Celsius", "Fahrenheit", "Kelvin"
        )

        else -> emptyList()
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            // =========================
            // TOP SAFE SPACING
            // =========================

            Spacer(
                modifier = Modifier.height(28.dp)
            )


            // =========================
            // HEADER
            // =========================

            Text(
                text = "Unit Converter",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Convert anything, instantly",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )


            // Katta, nafas oladigan bo'shliq
            Spacer(
                modifier = Modifier.height(48.dp)
            )


            // =========================
            // CATEGORY
            // =========================

            Text(
                text = "Category",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            CategorySelector(
                selectedCategory = selectedCategory, onCategorySelected = { category ->

                    selectedCategory = category

                    when (category) {

                        "Length" -> {
                            fromUnit = "Meters"
                            toUnit = "Kilometers"
                        }

                        "Weight" -> {
                            fromUnit = "Kilograms"
                            toUnit = "Grams"
                        }

                        "Temperature" -> {
                            fromUnit = "Celsius"
                            toUnit = "Fahrenheit"
                        }
                    }
                })


            Spacer(
                modifier = Modifier.height(40.dp)
            )


            // =========================
            // ENTER VALUE
            // =========================

            SectionTitle(
                text = "Enter value"
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Value",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    OutlinedTextField(
                        value = inputValue,

                        onValueChange = { value ->

                            // Faqat son, nuqta va vergul
                            if (value.all {
                                    it.isDigit() || it == '.' || it == ','
                                }) {
                                inputValue = value
                            }
                        },

                        placeholder = {
                            Text("0")
                        },

                        singleLine = true,

                        textStyle = MaterialTheme.typography.headlineMedium,

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(40.dp)
            )


            // =========================
            // FROM
            // =========================

            SectionTitle(
                text = "From"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            UnitSelector(
                unit = fromUnit, units = availableUnits, onUnitSelected = { selectedUnit ->
                    fromUnit = selectedUnit
                })


            // =========================
            // SWAP BUTTON
            // =========================

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp),

                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                        .clickable {

                            val temporaryUnit = fromUnit

                            fromUnit = toUnit

                            toUnit = temporaryUnit
                        },

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.SwapVert,

                        contentDescription = "Swap units",

                        tint = MaterialTheme.colorScheme.onPrimary,

                        modifier = Modifier.size(28.dp)
                    )
                }
            }


            // =========================
            // TO
            // =========================

            SectionTitle(
                text = "To"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            UnitSelector(
                unit = toUnit, units = availableUnits, onUnitSelected = { selectedUnit ->
                    toUnit = selectedUnit
                })


            Spacer(
                modifier = Modifier.height(44.dp)
            )


            // =========================
            // RESULT
            // =========================

            SectionTitle(
                text = "Result"
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            ResultCard(
                result = result, unit = toUnit
            )


            // Bottom safe spacing
            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}

@Composable
fun CategorySelector(
    selectedCategory: String, onCategorySelected: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        CategoryItem(
            text = "Length", selected = selectedCategory == "Length", onClick = {
                onCategorySelected("Length")
            }, modifier = Modifier.weight(1f)
        )

        CategoryItem(
            text = "Weight", selected = selectedCategory == "Weight", onClick = {
                onCategorySelected("Weight")
            }, modifier = Modifier.weight(1f)
        )

        CategoryItem(
            text = "Temp", selected = selectedCategory == "Temperature", onClick = {
                onCategorySelected("Temperature")
            }, modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CategoryItem(
    text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier
            .height(48.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(16.dp), color = backgroundColor
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text, color = textColor, fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun UnitSelector(
    unit: String, units: List<String>, onUnitSelected: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                },

            shape = RoundedCornerShape(20.dp),

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp, vertical = 18.dp
                    ),

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = unit,

                    style = MaterialTheme.typography.titleMedium,

                    fontWeight = FontWeight.Medium,

                    color = MaterialTheme.colorScheme.onSurface
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,

                    contentDescription = "Select unit",

                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        DropdownMenu(
            expanded = expanded,

            onDismissRequest = {
                expanded = false
            },

            modifier = Modifier.fillMaxWidth(0.92f)
        ) {

            units.forEach { item ->

                DropdownMenuItem(

                    text = {
                        Text(
                            text = item,

                            fontWeight = if (item == unit) FontWeight.Bold
                            else FontWeight.Normal
                        )
                    },

                    onClick = {

                        onUnitSelected(item)

                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun ResultCard(
    result: String, unit: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "Converted value",

                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = result.ifEmpty {
                    "0"
                },

                fontSize = 36.sp,

                fontWeight = FontWeight.Bold,

                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = unit,

                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun SectionTitle(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

fun convertValue(
    value: String, category: String, fromUnit: String, toUnit: String
): String {

    val number = value.replace(',', '.').toDoubleOrNull() ?: return ""

    val result = when (category) {

        // =========================
        // LENGTH
        // =========================

        "Length" -> {

            // Avval hamma qiymatni METERS ga o'tkazamiz
            val valueInMeters = when (fromUnit) {

                "Millimeters" -> number / 1000
                "Centimeters" -> number / 100
                "Meters" -> number
                "Kilometers" -> number * 1000
                "Miles" -> number * 1609.344
                "Feet" -> number * 0.3048
                "Inches" -> number * 0.0254

                else -> number
            }

            // Meters dan kerakli unitga o'tkazamiz
            when (toUnit) {

                "Millimeters" -> valueInMeters * 1000
                "Centimeters" -> valueInMeters * 100
                "Meters" -> valueInMeters
                "Kilometers" -> valueInMeters / 1000
                "Miles" -> valueInMeters / 1609.344
                "Feet" -> valueInMeters / 0.3048
                "Inches" -> valueInMeters / 0.0254

                else -> valueInMeters
            }
        }


        // =========================
        // WEIGHT
        // =========================

        "Weight" -> {

            // Avval GRAMS ga o'tkazamiz
            val valueInGrams = when (fromUnit) {

                "Milligrams" -> number / 1000
                "Grams" -> number
                "Kilograms" -> number * 1000
                "Pounds" -> number * 453.59237
                "Ounces" -> number * 28.349523125

                else -> number
            }

            // Grams dan kerakli unitga o'tkazamiz
            when (toUnit) {

                "Milligrams" -> valueInGrams * 1000
                "Grams" -> valueInGrams
                "Kilograms" -> valueInGrams / 1000
                "Pounds" -> valueInGrams / 453.59237
                "Ounces" -> valueInGrams / 28.349523125

                else -> valueInGrams
            }
        }


        // =========================
        // TEMPERATURE
        // =========================

        "Temperature" -> {

            // Avval CELSIUS ga o'tkazamiz
            val valueInCelsius = when (fromUnit) {

                "Celsius" -> number

                "Fahrenheit" -> (number - 32) * 5 / 9

                "Kelvin" -> number - 273.15

                else -> number
            }

            // Celsius dan kerakli temperaturaga
            when (toUnit) {

                "Celsius" -> valueInCelsius

                "Fahrenheit" -> (valueInCelsius * 9 / 5) + 32

                "Kelvin" -> valueInCelsius + 273.15

                else -> valueInCelsius
            }
        }

        else -> number
    }


    // =========================
    // FORMAT
    // =========================

    return java.text.DecimalFormat("#.##").format(result)
}