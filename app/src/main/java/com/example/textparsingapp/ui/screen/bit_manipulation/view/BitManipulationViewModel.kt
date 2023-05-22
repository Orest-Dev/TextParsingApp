package com.example.textparsingapp.ui.screen.bit_manipulation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.textparsingapp.data.repository.bit_manipulation.BitManipulationRepository
import com.example.textparsingapp.ui.screen.bit_manipulation.event.BitManipulationEvent
import com.example.textparsingapp.ui.screen.bit_manipulation.state.BitManipulationState
import com.example.textparsingapp.ui.screen.bit_manipulation.state.BitsResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BitManipulationViewModel @Inject constructor(
    private val repository: BitManipulationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BitManipulationState.initial())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BitManipulationEvent>(replay = 1)

    init {
        val digitsPattern = Regex("^\\d+\$")

        viewModelScope.launch {
            _uiEvent.onEach { event ->
                when (event) {
                    is BitManipulationEvent.OnFirstInputChangedEvent -> {
                        if (event.input.isEmpty() || event.input.matches(digitsPattern)) {
                            _uiState.update {
                                it.copy(
                                    firstInput = event.input
                                )
                            }
                        }
                    }

                    is BitManipulationEvent.OnSecondInputChangedEvent -> {
                        if (event.input.isEmpty() || event.input.matches(digitsPattern)) {
                            _uiState.update {
                                it.copy(secondInput = event.input)
                            }
                        }
                    }

                    else -> {}
                }
            }.collect { handleUiEvent(it) }
        }
    }

    fun calculateBits(firstInput: String, secondInput: String) {
        sendEvent(BitManipulationEvent.CalculateBitsEvent(firstInput, secondInput))
    }

    fun onFirstInputChange(input: String) {
        sendEvent(BitManipulationEvent.OnFirstInputChangedEvent(input))
    }

    fun onSecondInputChange(input: String) {
        sendEvent(BitManipulationEvent.OnSecondInputChangedEvent(input))
    }

    private fun sendEvent(event: BitManipulationEvent) {
        _uiEvent.tryEmit(event)
    }

    private suspend fun handleUiEvent(event: BitManipulationEvent) = when (event) {
        is BitManipulationEvent.CalculateBitsEvent -> onCalculateButtonPressed(
            event.firstInput,
            event.secondInput
        ).reduce()

        is BitManipulationEvent.OnFirstInputChangedEvent -> onEnteredStringChanged()
        is BitManipulationEvent.OnSecondInputChangedEvent -> onEnteredStringChanged()
    }

    private suspend fun onCalculateButtonPressed(firstInput: String, secondInput: String) =
        runCatching {
            _uiState.update { it.copy(bitsResultState = BitsResultState.Loading) }

            //for visual loading effect
            delay(500)

            repository.calculateBits(firstInput, secondInput)
        }

    private fun onEnteredStringChanged() {
        _uiState.update { it.copy(bitsResultState = BitsResultState.Initial) }
        if (_uiState.value.firstInput.isNotEmpty() && _uiState.value.secondInput.isNotEmpty()) {
            _uiState.update { it.copy(isInputFormValid = true) }
        } else {
            _uiState.update { it.copy(isInputFormValid = false) }
        }
    }

    private fun Result<String>.reduce() {
        this.onSuccess { bits ->
            _uiState.update { it.copy(bitsResultState = BitsResultState.BitsResult(bits)) }
        }
        this.onFailure { _uiState.update { it.copy(bitsResultState = BitsResultState.Error) } }
    }
}