package com.example.textparsingapp.ui.screen.bit_manipulation.state

import androidx.compose.runtime.Immutable

@Immutable
data class BitManipulationState(
    val isInputFormValid: Boolean,
    val firstInput: String,
    val secondInput: String,
    val bitsResultState: BitsResultState,
) {

    fun isCalculateButtonEnabled(): Boolean {
        return isInputFormValid && bitsResultState !is BitsResultState.Loading
    }

    companion object {
        fun initial(): BitManipulationState = BitManipulationState(
            isInputFormValid = false,
            firstInput = "",
            secondInput = "",
            bitsResultState = BitsResultState.Initial,
        )
    }
}

@Immutable
sealed class BitsResultState {
    object Initial : BitsResultState()

    object Loading : BitsResultState()

    object Error : BitsResultState()

    data class BitsResult(val bits: String) : BitsResultState()
}