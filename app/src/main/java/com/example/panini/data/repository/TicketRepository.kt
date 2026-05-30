package com.example.panini.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import com.example.panini.domain.model.Ticket
import com.example.panini.domain.model.TicketCategory
import com.example.panini.domain.model.TicketPriority
import com.example.panini.domain.model.TicketStatus

class TicketRepository {

    // MockEngine: Datos realistas para Panini en memoria
    private val mockTickets = mutableListOf(
        Ticket(
            id = "TCK-1045",
            title = "Faltante de cajas de sobres",
            description = "El envío de la semana pasada vino con 5 cajas menos de sobres regulares.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.OPEN,
            providerName = "Distribuidora Central SJ",
            createdAt = "2026-05-30T08:00:00Z",
            category = TicketCategory.INVENTORY_SHORTAGE
        ),
        Ticket(
            id = "TCK-1046",
            title = "Sobres mal sellados",
            description = "Lote #8942 de sobres tapa dura vino abierto o mal pegado.",
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.IN_PROGRESS,
            providerName = "Imprenta Las Américas",
            createdAt = "2026-05-29T14:30:00Z",
            category = TicketCategory.QUALITY_ISSUE
        ),
        Ticket(
            id = "TCK-1047",
            title = "Retraso en entrega regional",
            description = "El camión hacia Huetar Norte no ha llegado tras 48 hrs.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.OPEN,
            providerName = "Logística Express",
            createdAt = "2026-05-28T10:15:00Z",
            category = TicketCategory.LOGISTICS_ERROR
        )
    )

    private val _ticketsFlow = MutableStateFlow(mockTickets.toList())
    val ticketsFlow: StateFlow<List<Ticket>> = _ticketsFlow.asStateFlow()

    fun getTicketsFlow(): StateFlow<List<Ticket>> {
        return ticketsFlow
    }

    fun getTicketById(id: String): Ticket? {
        return mockTickets.find { it.id == id }
    }

    fun createTicket(title: String, description: String, priority: TicketPriority, category: TicketCategory, providerId: String): Ticket {
        val newTicket = Ticket(
            id = "TCK-${1000 + mockTickets.size + 50}",
            title = title,
            description = description,
            priority = priority,
            status = TicketStatus.OPEN,
            providerName = "Proveedor Simulado ($providerId)",
            createdAt = "2026-05-30T10:00:00Z",
            category = category
        )
        mockTickets.add(newTicket)
        _ticketsFlow.value = mockTickets.toList()
        return newTicket
    }

    fun updateTicketStatus(id: String, newStatus: TicketStatus): Boolean {
        val index = mockTickets.indexOfFirst { it.id == id }
        if (index != -1) {
            mockTickets[index] = mockTickets[index].copy(status = newStatus)
            _ticketsFlow.value = mockTickets.toList()
            return true
        }
        return false
    }

    fun updateTicketPriority(id: String, newPriority: TicketPriority): Boolean {
        val index = mockTickets.indexOfFirst { it.id == id }
        if (index != -1) {
            mockTickets[index] = mockTickets[index].copy(priority = newPriority)
            _ticketsFlow.value = mockTickets.toList()
            return true
        }
        return false
    }
}
