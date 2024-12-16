package com.example.proyecto

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun EmployerCalculationsScreen(navController: NavHostController, calculationHistoryViewModel: CalculationHistoryViewModel) {
    var baseSalary by remember { mutableStateOf("") }
    var totalPayrollCost by remember { mutableStateOf("") }
    var parafiscalContributions by remember { mutableStateOf("") }
    var senaContribution by remember { mutableStateOf("") }
    var icbfContribution by remember { mutableStateOf("") }
    var cajaContribution by remember { mutableStateOf("") }
    var socialSecurity by remember { mutableStateOf("") }
    var pensionContribution by remember { mutableStateOf("") }
    var healthContribution by remember { mutableStateOf("") }
    var provisions by remember { mutableStateOf("") }
    var prima by remember { mutableStateOf("") }
    var cesantias by remember { mutableStateOf("") }
    var intereses by remember { mutableStateOf("") }
    var vacaciones by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Cálculos de Empleador", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = baseSalary,
            onValueChange = {
                baseSalary = it
                errorMessage = validateNumericInput2(it)
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
                    val salary = baseSalary.toDoubleOrNull() ?: 0.0
                    parafiscalContributions = calculateParafiscalContributions(salary)
                    senaContribution = calculateSenaContribution(salary)
                    icbfContribution = calculateIcbfContribution(salary)
                    cajaContribution = calculateCajaContribution(salary)
                    socialSecurity = calculateSocialSecurity(salary)
                    pensionContribution = calculatePensionContribution(salary)
                    healthContribution = calculateHealthContribution(salary)
                    provisions = calculateProvisions(salary)
                    prima = calculatePrima(salary)
                    cesantias = calculateCesantias(salary)
                    intereses = calculateIntereses(salary)
                    vacaciones = calculateVacaciones(salary)
                    totalPayrollCost = (salary + parafiscalContributions.toDouble() + socialSecurity.toDouble() + provisions.toDouble()).toString()

                    // Almacena el cálculo en el historial
                    val result = "Aportes Parafiscales: $parafiscalContributions \n" +
                            "(SENA: $senaContribution, ICBF: $icbfContribution, Caja: $cajaContribution), \n" +
                            "Seguridad Social: $socialSecurity \n" +
                            "(Pensión: $pensionContribution, Salud: $healthContribution), \n" +
                            "Provisiones: $provisions \n" +
                            "(Prima: $prima, Cesantías: $cesantias, Intereses: $intereses, \n" +
                            "Vacaciones: $vacaciones), \n" +
                            "Costo Total de Nómina: $totalPayrollCost"
                    calculationHistoryViewModel.addEntry(CalculationEntry("Empleador", result))
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

fun calculateParafiscalContributions(salary: Double): String {
    return (salary * 0.09).toString()
}

fun calculateSenaContribution(salary: Double): String {
    return (salary * 0.02).toString()
}

fun calculateIcbfContribution(salary: Double): String {
    return (salary * 0.03).toString()
}

fun calculateCajaContribution(salary: Double): String {
    return (salary * 0.04).toString()
}

fun calculateSocialSecurity(salary: Double): String {
    return (salary * 0.205).toString()
}

fun calculatePensionContribution(salary: Double): String {
    return (salary * 0.12).toString()
}

fun calculateHealthContribution(salary: Double): String {
    return (salary * 0.085).toString()
}

fun calculateProvisions(salary: Double): String {
    return (salary * 0.2183).toString()
}

fun calculatePrima(salary: Double): String {
    return (salary * 0.0833).toString()
}

fun calculateCesantias(salary: Double): String {
    return (salary * 0.0833).toString()
}

fun calculateIntereses(salary: Double): String {
    return (salary * 0.01).toString()
}

fun calculateVacaciones(salary: Double): String {
    return (salary * 0.0417).toString()
}

fun validateNumericInput2(input: String): String {
    return if (input.toDoubleOrNull() != null) "" else "Entrada inválida: Solo se permiten números"
}
