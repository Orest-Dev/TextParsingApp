package com.example.textparsingapp.ui.screen.text_parsing.state

import androidx.compose.runtime.Immutable

@Immutable
data class TextParsingState(
    val isInputValid: Boolean,
    val input: String,
    val wordsResultState: WordsResultState,
) {

    fun isParseButtonEnabled(): Boolean {
        return isInputValid && wordsResultState !is WordsResultState.Loading
    }

    companion object {
        fun initial(): TextParsingState = TextParsingState(
            isInputValid = false,
            input = "",
            wordsResultState = WordsResultState.Initial,
        )
    }
}

@Immutable
sealed class WordsResultState {
    object Initial : WordsResultState()

    object Loading : WordsResultState()

    object Error : WordsResultState()

    object NoResult : WordsResultState()

    data class WordsResult(val words: String) : WordsResultState()
}