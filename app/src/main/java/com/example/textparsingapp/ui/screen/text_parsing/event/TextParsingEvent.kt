package com.example.textparsingapp.ui.screen.text_parsing.event

import androidx.compose.runtime.Immutable

@Immutable
sealed class TextParsingEvent {

    data class ParseTextEvent(val input: String) : TextParsingEvent()

    data class OnInputChangedEvent(val input: String) : TextParsingEvent()

}
