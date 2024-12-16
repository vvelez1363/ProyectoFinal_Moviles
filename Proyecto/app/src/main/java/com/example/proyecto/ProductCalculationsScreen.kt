package com.example.proyecto

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProductCalculationsScreen(navController: NavHostController, calculationHistoryViewModel: CalculationHistoryViewModel) {
    var productName by remember { mutableStateOf("") }
    var basePrice by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Cálculos de Productos", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = productName,
            onValueChange = {
                productName = it
                errorMessage = validateProductName(it)
            },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty()
        )
        OutlinedTextField(
            value = basePrice,
            onValueChange = {
                basePrice = it
                errorMessage = validateNumericInput(it)
            },
            label = { Text("Precio Base") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty()
        )
        OutlinedTextField(
            value = sellingPrice,
            onValueChange = {
                sellingPrice = it
                errorMessage = validateNumericInput(it)
            },
            label = { Text("Precio de Venta") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        successMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                if (productName.isNotEmpty() && basePrice.isNotEmpty() && sellingPrice.isNotEmpty() && errorMessage.isEmpty()) {
                    val cost = calculateCost(basePrice)
                    val fixedCosts = calculateFixedCosts()
                    val variableCosts = calculateVariableCosts()
                    val investment = calculateInvestment(basePrice)
                    val revenue = calculateRevenue(sellingPrice)
                    val priceWithVAT = calculatePriceWithVAT(basePrice)
                    val margin = calculateMargin(sellingPrice, cost)
                    val breakEvenPoint = calculateBreakEvenPoint(fixedCosts, sellingPrice, variableCosts)
                    val roi = calculateROI(revenue, investment)
                    val result = "Producto: $productName, \n" +
                            "Precio con IVA: $priceWithVAT, \n" +
                            "Margen de Ganancia: $margin, \n" +
                            "Punto de Equilibrio: $breakEvenPoint, \n" +
                            "ROI: $roi"
                    calculationHistoryViewModel.addEntry(CalculationEntry("Producto", result))
                    successMessage = "Producto añadido con éxito"
                }
            }) {
                Text("Añadir Producto")
            }

            Button(onClick = { navController.navigate("home") }) {
                Text("Regresar")
            }
        }
    }
}

fun validateProductName(input: String): String {
    return if (input.all { it.isLetterOrDigit() || it.isWhitespace() }) "" else "Nombre inválido: Solo se permiten caracteres alfanuméricos"
}

fun validateNumericInput(input: String): String {
    return if (input.toDoubleOrNull() != null) "" else "Entrada inválida: Solo se permiten números"
}

fun calculatePriceWithVAT(basePrice: String): String {
    val price = basePrice.toDoubleOrNull() ?: return "Entrada inválida"
    return (price * 1.19).toString()
}

fun calculateMargin(sellingPrice: String, cost: String): String {
    val salePrice = sellingPrice.toDoubleOrNull() ?: return "Entrada inválida"
    val costPrice = cost.toDoubleOrNull() ?: return "Entrada inválida"
    return ((salePrice - costPrice) / salePrice * 100).toString()
}

fun calculateBreakEvenPoint(fixedCosts: String, sellingPrice: String, variableCosts: String): String {
    val fixed = fixedCosts.toDoubleOrNull() ?: return "Entrada inválida"
    val price = sellingPrice.toDoubleOrNull() ?: return "Entrada inválida"
    val variable = variableCosts.toDoubleOrNull() ?: return "Entrada inválida"
    return (fixed / (price - variable)).toString()
}

fun calculateROI(revenue: String, investment: String): String {
    val rev = revenue.toDoubleOrNull() ?: return "Entrada inválida"
    val inv = investment.toDoubleOrNull() ?: return "Entrada inválida"
    return ((rev - inv) / inv * 100).toString()
}

fun calculateCost(basePrice: String): String {
    val price = basePrice.toDoubleOrNull() ?: return "Entrada inválida"
    return (price * 0.5).toString() // Ejemplo de cálculo
}

fun calculateFixedCosts(): String {
    return "1000" // Ejemplo de valor fijo
}

fun calculateVariableCosts(): String {
    return "5" // Ejemplo de valor variable
}

fun calculateInvestment(basePrice: String): String {
    val price = basePrice.toDoubleOrNull() ?: return "Entrada inválida"
    return (price * 100).toString() // Ejemplo de cálculo
}

fun calculateRevenue(sellingPrice: String): String {
    val price = sellingPrice.toDoubleOrNull() ?: return "Entrada inválida"
    return (price * 10).toString() // Ejemplo de cálculo
}

data class Product(val name: String, val basePrice: Double, val sellingPrice: Double, val cost: Double)
