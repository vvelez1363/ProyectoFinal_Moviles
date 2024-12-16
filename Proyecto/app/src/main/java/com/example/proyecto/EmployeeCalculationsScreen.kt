package com.example.proyecto

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun EmployeeCalculationsScreen(navController: NavHostController, calculationHistoryViewModel: CalculationHistoryViewModel) {
    var baseSalary by remember { mutableStateOf("") }
    var netSalary by remember { mutableStateOf("") }
    var extraHourDay by remember { mutableStateOf("") }
    var extraHourNight by remember { mutableStateOf("") }
    var holidayHour by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Cálculos de Empleado", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = baseSalary,
            onValueChange = {
                baseSalary = it
                errorMessage = validateNumericInput3(it)
            },
            label = { Text("Salario Base") },
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
                if (baseSalary.isNotEmpty() && errorMessage.isEmpty()) {
                    netSalary = calculateNetSalary(baseSalary)
                    extraHourDay = calculateExtraHourDay(baseSalary)
                    extraHourNight = calculateExtraHourNight(baseSalary)
                    holidayHour = calculateHolidayHour(baseSalary)

                    // Almacena el cálculo en el historial
                    val result = "Salario Neto: $netSalary, \n " +
                            "Hora Extra Diurna: $extraHourDay, \n" +
                            "Hora Extra Nocturna: $extraHourNight, \n" +
                            "Hora Dominical/Festiva: $holidayHour"
                    calculationHistoryViewModel.addEntry(CalculationEntry("Empleado", result))
                    successMessage = "Cálculo añadido con éxito"
                }
            }) {
                Text("Añadir Cálculo")
            }

            Button(onClick = { navController.navigate("home") }) {
                Text("Regresar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

    fun calculateNetSalary(baseSalary: String): String {
    val salary = baseSalary.toDoubleOrNull() ?: return "Entrada inválida"
    return (salary - salary * 0.08).toString()
}

fun calculateExtraHourDay(baseSalary: String): String {
    val salary = baseSalary.toDoubleOrNull() ?: return "Entrada inválida"
    return ((salary / 240) * 1.25).toString()
}

fun calculateExtraHourNight(baseSalary: String): String {
    val salary = baseSalary.toDoubleOrNull() ?: return "Entrada inválida"
    return ((salary / 240) * 1.75).toString()
}

fun calculateHolidayHour(baseSalary: String): String {
    val salary = baseSalary.toDoubleOrNull() ?: return "Entrada inválida"
    return ((salary / 240) * 2).toString()
}

fun validateNumericInput3(input: String): String {
    return if (input.toDoubleOrNull() != null) "" else "Entrada inválida: Solo se permiten números"
}
