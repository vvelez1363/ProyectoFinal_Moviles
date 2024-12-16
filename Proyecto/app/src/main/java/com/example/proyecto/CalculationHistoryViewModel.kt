package com.example.proyecto

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CalculationHistoryViewModel : ViewModel() {
    private val _history = mutableStateListOf<CalculationEntry>()
    val history: List<CalculationEntry> get() = _history

    fun addEntry(entry: CalculationEntry) {
        _history.add(entry)
    }

    fun clearHistory() {
        _history.clear()
    }
}

data class CalculationEntry(val type: String, val result: String)
