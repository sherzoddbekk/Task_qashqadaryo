package com.example.taskqashqadaryo.viewmodels.state

import com.example.taskqashqadaryo.models.Card

data class CreateCardState(
    val card: Card? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)