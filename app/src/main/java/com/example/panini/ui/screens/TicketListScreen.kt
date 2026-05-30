package com.example.panini.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panini.domain.model.Ticket
import com.example.panini.domain.model.TicketPriority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(
    viewModel: TicketViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToCreate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incidencias", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            // Feature Flag: Mostrar botón solo si está habilitado
            if (uiState.isTicketCreationEnabled) {
                FloatingActionButton(
                    onClick = onNavigateToCreate,
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    contentColor = MaterialTheme.colorScheme.surface
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Crear Ticket")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Panel de Control para Feature Flags (Solo propósitos de Testing PoC)
            FeatureFlagsPanel(
                uiState = uiState,
                onToggleCreation = { viewModel.toggleTicketCreationFeature() },
                onTogglePriority = { viewModel.togglePriorityUpdateFeature() }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.tickets, key = { it.id }) { ticket ->
                    TicketCard(ticket = ticket, onClick = { onNavigateToDetail(ticket.id) })
                }
            }
        }
    }
}

@Composable
fun FeatureFlagsPanel(
    uiState: TicketUiState,
    onToggleCreation: () -> Unit,
    onTogglePriority: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("⚙️ Feature Flags", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = uiState.isTicketCreationEnabled, onCheckedChange = { onToggleCreation() })
                Text("Permitir Crear Tickets", fontSize = 14.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = uiState.isPriorityUpdateEnabled, onCheckedChange = { onTogglePriority() })
                Text("Permitir Cambiar Prioridad", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun TicketCard(ticket: Ticket, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ticket.id,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // Badge de Estado y Prioridad
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PriorityBadge(priority = ticket.priority)
                    StatusBadge(status = ticket.status.name)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = ticket.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun PriorityBadge(priority: TicketPriority) {
    val bgColor = when (priority) {
        TicketPriority.HIGH -> Color.DarkGray
        TicketPriority.MEDIUM -> Color.Gray
        TicketPriority.LOW -> Color.LightGray
    }
    val textColor = when (priority) {
        TicketPriority.LOW -> Color.Black
        else -> Color.White
    }
    
    Box(
        modifier = Modifier
            .background(bgColor, shape = MaterialTheme.shapes.small)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = priority.name, fontSize = 10.sp, color = textColor, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatusBadge(status: String) {
    Box(
        modifier = Modifier
            .background(Color.Black, shape = MaterialTheme.shapes.small)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = status, fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
