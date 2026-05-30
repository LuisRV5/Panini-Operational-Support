package com.example.panini.data.remote.api

import com.example.panini.data.remote.model.CreateTicketRequest
import com.example.panini.data.remote.model.TicketDto
import com.example.panini.data.remote.model.UpdatePriorityRequest
import com.example.panini.data.remote.model.UpdateStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/tickets")
    suspend fun getTickets(): Response<List<TicketDto>>

    @GET("api/tickets/{id}")
    suspend fun getTicketDetail(@Path("id") id: String): Response<TicketDto>

    @POST("api/tickets")
    suspend fun createTicket(@Body request: CreateTicketRequest): Response<TicketDto>

    @PATCH("api/tickets/{id}/status")
    suspend fun updateStatus(
        @Path("id") id: String,
        @Body request: UpdateStatusRequest
    ): Response<Unit>

    @PATCH("api/tickets/{id}/priority")
    suspend fun updatePriority(
        @Path("id") id: String,
        @Body request: UpdatePriorityRequest
    ): Response<Unit>
}
