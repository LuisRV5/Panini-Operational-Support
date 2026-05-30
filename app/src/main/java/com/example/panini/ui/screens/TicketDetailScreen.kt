package com.example.panini.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panini.domain.model.TicketPriority
import com.example.panini.domain.model.TicketStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDetailScreen(
    ticketId: String,
    viewModel: TicketViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val ticket = uiState.tickets.find { it.id == ticketId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Ticket", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        if (ticket == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Ticket no encontrado")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = ticket.id, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = ticket.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                
                Divider()
                
                Text(text = "Descripción", fontWeight = FontWeight.Bold)
                Text(text = ticket.description)
                
                Text(text = "Proveedor", fontWeight = FontWeight.Bold)
                Text(text = ticket.providerName)
                
                Text(text = "Categoría", fontWeight = FontWeight.Bold)
                Text(text = ticket.category.name)
                
                Text(text = "Fecha de Creación", fontWeight = FontWeight.Bold)
                Text(text = ticket.createdAt)

                Divider()

                // Actualizar Estado
                Text(text = "Estado del Ticket", fontWeight = FontWeight.Bold)
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TicketStatus.values().forEach { status ->
                        FilterChip(
                            selected = ticket.status == status,
                            onClick = { viewModel.updateTicketStatus(ticket.id, status) },
                            label = { Text(status.name) }
                        )
                    }
                }

                // Feature Flag: Actualizar Prioridad
                Text(text = "Prioridad (Reordena la lista principal)", fontWeight = FontWeight.Bold)
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TicketPriority.values().forEach { priority ->
                        FilterChip(
                            selected = ticket.priority == priority,
                            onClick = { 
                                if (uiState.isPriorityUpdateEnabled) {
                                    viewModel.updateTicketPriority(ticket.id, priority) 
                                }
                            },
                            label = { Text(priority.name) },
                            enabled = uiState.isPriorityUpdateEnabled // Deshabilita si Feature Flag está apagada
                        )
                    }
                }
                
                if (!uiState.isPriorityUpdateEnabled) {
                    Text(
                        text = "La actualización de prioridad está deshabilitada (Feature Flag).",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
