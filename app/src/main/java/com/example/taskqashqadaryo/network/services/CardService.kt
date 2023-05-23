package com.example.taskqashqadaryo.network.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.taskqashqadaryo.network.models.CardRequest
import com.example.taskqashqadaryo.network.models.CardResponse

interface CardService {

    @GET("cards")
    suspend fun getAllCards(): Response<List<CardResponse>>

    @POST("cards")
    suspend fun saveCard(@Body cardRequest: CardRequest): Response<CardResponse>

}