package com.example.proyecto

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Cálculos Financieros Empresariales", style = MaterialTheme.typography.headlineMedium)

            IconButton(onClick = { navController.navigate("history") }) {
                Icon(imageVector = Icons.Default.List, contentDescription = "Ver Historial")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("products") }) {
            Text("Cálculos de Productos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("employer") }) {
            Text("Cálculos de Empleador")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("employee") }) {
            Text("Cálculos de Empleado")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("history") }) {
            Text("Historial de Cálculos")
        }
    }
}