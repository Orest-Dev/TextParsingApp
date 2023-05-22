package com.example.textparsingapp.ui.screen.bit_manipulation.event

import androidx.compose.runtime.Immutable

@Immutable
sealed class BitManipulationEvent {

    data class CalculateBitsEvent(val firstInput: String, val secondInput: String) :
        BitManipulationEvent()

    data class OnFirstInputChangedEvent(val input: String) : BitManipulationEvent()

    data class OnSecondInputChangedEvent(val input: String) : BitManipulationEvent()

}