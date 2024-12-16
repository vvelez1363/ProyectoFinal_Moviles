package com.example.proyecto

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController, calculationHistoryViewModel: CalculationHistoryViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("products") { ProductCalculationsScreen(navController, calculationHistoryViewModel) }
        composable("employer") { EmployerCalculationsScreen(navController, calculationHistoryViewModel) }
        composable("employee") { EmployeeCalculationsScreen(navController, calculationHistoryViewModel) }
        composable("history") { HistoryScreen(navController, calculationHistoryViewModel) }
    }
}