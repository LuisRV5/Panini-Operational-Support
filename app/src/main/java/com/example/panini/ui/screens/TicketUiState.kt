package com.example.panini.ui.screens

import com.example.panini.domain.model.Ticket

data class TicketUiState(
    val tickets: List<Ticket> = emptyList(),
    val isLoading: Boolean = false,
    val isTicketCreationEnabled: Boolean = true, // Feature Flag 1
    val isPriorityUpdateEnabled: Boolean = true // Feature Flag 2
)
