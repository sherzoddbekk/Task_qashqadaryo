package com.example.taskqashqadaryo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.taskqashqadaryo.models.Card
import com.example.taskqashqadaryo.repositories.CreateCardRepository
import com.example.taskqashqadaryo.utils.NetworkResource
import com.example.taskqashqadaryo.viewmodels.state.CreateCardState
import javax.inject.Inject

@HiltViewModel
class CreateCardViewModel @Inject constructor(private val createCardRepository: CreateCardRepository) : ViewModel() {

    private var cardsToSaveJob: Job? = null

    private val _uiState = MutableStateFlow(CreateCardState())
    val uiState = _uiState.asStateFlow()

    fun saveToLocal(card: Card){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when(val result = createCardRepository.saveCardToLocal(card)){
                is NetworkResource.Success ->{
                    _uiState.update { it.copy(card = result.data!!, isLoading = false) }
                }
                is NetworkResource.Error ->{
                    _uiState.update { it.copy(errorMessage = result.uiText.toString(), isLoading = false) }
                }
            }
        }
    }

    fun saveToServer(card: Card){
        cardsToSaveJob?.cancel()
        cardsToSaveJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when(val result = createCardRepository.saveCardToServer(card)){
                is NetworkResource.Success ->{
                    _uiState.update { it.copy(card = result.data!!, isLoading = false) }
                }
                is NetworkResource.Error ->{
                    _uiState.update { it.copy(errorMessage = result.uiText.toString(), isLoading = false) }
                }
            }
        }
    }

}