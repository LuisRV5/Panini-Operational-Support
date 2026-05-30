package com.example.panini.domain.model

enum class TicketPriority {
    HIGH, MEDIUM, LOW
}

enum class TicketStatus {
    OPEN, IN_PROGRESS, RESOLVED, CLOSED
}

enum class TicketCategory {
    INVENTORY_SHORTAGE, LOGISTICS_ERROR, QUALITY_ISSUE, OTHER
}

data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val priority: TicketPriority,
    val status: TicketStatus,
    val providerName: String,
    val createdAt: String,
    val category: TicketCategory
)
