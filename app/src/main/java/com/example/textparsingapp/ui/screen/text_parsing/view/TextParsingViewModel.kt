package com.example.textparsingapp.ui.screen.text_parsing.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.textparsingapp.data.repository.text_parsing.TextParsingRepository
import com.example.textparsingapp.ui.screen.text_parsing.event.TextParsingEvent
import com.example.textparsingapp.ui.screen.text_parsing.state.TextParsingState
import com.example.textparsingapp.ui.screen.text_parsing.state.WordsResultState
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
class TextParsingViewModel @Inject constructor(
    private val repository: TextParsingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TextParsingState.initial())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<TextParsingEvent>(replay = 1)

    init {
        viewModelScope.launch {
            repository.initDictionary()

            _uiEvent.onEach { event ->
                when (event) {
                    is TextParsingEvent.ParseTextEvent -> _uiState.update { it.copy(input = event.input) }
                    is TextParsingEvent.OnInputChangedEvent -> _uiState.update { it.copy(input = event.input) }
                }
            }.collect { handleUiEvent(it) }
        }
    }

    fun parseText(input: String) {
        sendEvent(TextParsingEvent.ParseTextEvent(input))
    }

    fun validate(input: String) {
        sendEvent(TextParsingEvent.OnInputChangedEvent(input))
    }

    private fun sendEvent(event: TextParsingEvent) {
        _uiEvent.tryEmit(event)
    }

    private suspend fun handleUiEvent(event: TextParsingEvent) = when (event) {
        is TextParsingEvent.ParseTextEvent -> onParseButtonPressed(event.input).reduce()
        is TextParsingEvent.OnInputChangedEvent -> onEnteredStringChanged(event.input)
    }

    private suspend fun onParseButtonPressed(input: String) = runCatching {
        _uiState.update { it.copy(wordsResultState = WordsResultState.Loading) }

        //for visual loading effect
        delay(500)

        repository.findAllWords(input)
    }

    private fun onEnteredStringChanged(input: String) {
        _uiState.update { it.copy(isInputValid = input.isNotEmpty()) }
        _uiState.update { it.copy(wordsResultState = WordsResultState.Initial) }
    }

    private fun Result<String>.reduce() {
        this.onSuccess { words ->
            if (words.isEmpty()) {
                _uiState.update { it.copy(wordsResultState = WordsResultState.NoResult) }
            } else {
                _uiState.update { it.copy(wordsResultState = WordsResultState.WordsResult(words)) }
            }
        }
        this.onFailure { _uiState.update { it.copy(wordsResultState = WordsResultState.Error) } }
    }
}