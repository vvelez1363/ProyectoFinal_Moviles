package com.example.proyecto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HistoryScreen(navController: NavHostController, calculationHistoryViewModel: CalculationHistoryViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Historial de Cálculos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn ya maneja el desplazamiento automáticamente
        LazyColumn {
            items(calculationHistoryViewModel.history) { entry ->
                Text("${entry.type}: ${entry.result}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.navigate("home") }) {
                Text("Regresar a Inicio")
            }
            IconButton(onClick = { calculationHistoryViewModel.clearHistory() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar Historial")
            }
        }
    }
}
