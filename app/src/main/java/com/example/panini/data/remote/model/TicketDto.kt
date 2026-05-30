package com.example.panini.data.remote.model

import com.google.gson.annotations.SerializedName

data class TicketDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("priority") val priority: String,
    @SerializedName("status") val status: String,
    @SerializedName("provider") val provider: ProviderDto?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("category") val category: String?
)

data class ProviderDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

data class CreateTicketRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("providerId") val providerId: String,
    @SerializedName("category") val category: String
)

data class UpdateStatusRequest(
    @SerializedName("status") val status: String
)

data class UpdatePriorityRequest(
    @SerializedName("priority") val priority: String
)
