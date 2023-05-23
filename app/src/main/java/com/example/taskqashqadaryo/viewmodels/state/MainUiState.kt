package com.example.taskqashqadaryo.viewmodels.state

import com.example.taskqashqadaryo.models.Card

data class MainUiState(
    val cardList: List<Card> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)