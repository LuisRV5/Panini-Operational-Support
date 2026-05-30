package com.example.panini.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panini.data.repository.TicketRepository
import com.example.panini.domain.model.TicketCategory
import com.example.panini.domain.model.TicketPriority
import com.example.panini.domain.model.TicketStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TicketViewModel(
    private val repository: TicketRepository = TicketRepository() // Instancia por defecto por simplicidad de la PoC
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketUiState(isLoading = true))
    val uiState: StateFlow<TicketUiState> = _uiState.asStateFlow()

    init {
        // Escucha activa del repositorio (Estrategia Basada en Eventos)
        viewModelScope.launch {
            repository.ticketsFlow.collectLatest { ticketList ->
                // Actualiza y ordena la lista automáticamente cada vez que el repo emite
                val sortedList = ticketList.sortedBy { it.priority } // HIGH(0), MEDIUM(1), LOW(2)
                _uiState.value = _uiState.value.copy(
                    tickets = sortedList,
                    isLoading = false
                )
            }
        }
    }

    // --- Funcionalidades Basadas en Eventos ---

    fun createTicket(title: String, description: String, priority: TicketPriority, category: TicketCategory, providerId: String) {
        if (!_uiState.value.isTicketCreationEnabled) return
        
        _uiState.value = _uiState.value.copy(isLoading = true)
        // La creación en el repositorio actualizará el Flow, que automáticamente desencadenará la recolección en el init.
        repository.createTicket(title, description, priority, category, providerId)
    }

    fun updateTicketPriority(id: String, newPriority: TicketPriority) {
        if (!_uiState.value.isPriorityUpdateEnabled) return

        _uiState.value = _uiState.value.copy(isLoading = true)
        // Esta acción modificará el StateFlow del Repositorio y re-ordenará visualmente el listado
        repository.updateTicketPriority(id, newPriority)
    }

    fun updateTicketStatus(id: String, newStatus: TicketStatus) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        repository.updateTicketStatus(id, newStatus)
    }

    // --- Feature Flags Toggles ---
    
    fun toggleTicketCreationFeature() {
        val current = _uiState.value.isTicketCreationEnabled
        _uiState.value = _uiState.value.copy(isTicketCreationEnabled = !current)
    }

    fun togglePriorityUpdateFeature() {
        val current = _uiState.value.isPriorityUpdateEnabled
        _uiState.value = _uiState.value.copy(isPriorityUpdateEnabled = !current)
    }
}
