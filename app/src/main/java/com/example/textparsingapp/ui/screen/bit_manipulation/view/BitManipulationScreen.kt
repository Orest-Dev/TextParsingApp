package com.example.textparsingapp.ui.screen.bit_manipulation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.textparsingapp.R
import com.example.textparsingapp.ui.screen.bit_manipulation.state.BitManipulationState
import com.example.textparsingapp.ui.screen.bit_manipulation.view.components.CalculationResult
import com.example.textparsingapp.ui.screen.bit_manipulation.view.components.InputNumberBox

@Composable
fun BitManipulationRoute(
    viewModel: BitManipulationViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    BitManipulationScreen(
        uiState = uiState,
        calculateBits = viewModel::calculateBits,
        onFirstInputChange = viewModel::onFirstInputChange,
        onSecondInputChange = viewModel::onSecondInputChange
    )
}

@Composable
fun BitManipulationScreen(
    uiState: BitManipulationState,
    calculateBits: (firstInput: String, secondInput: String) -> Unit,
    onFirstInputChange: (query: String) -> Unit,
    onSecondInputChange: (query: String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = (R.string.calculate_text)),
                color = Color.Black,
                modifier = Modifier.padding(all = 16.dp),
                textAlign = TextAlign.Center
            )

            InputNumberBox(
                inputNumber = uiState.firstInput,
                onInputChange = onFirstInputChange,
                keyboardActions = KeyboardActions(onDone = {
                    calculateBits(uiState.firstInput, uiState.secondInput)
                })
            )
            InputNumberBox(
                inputNumber = uiState.secondInput,
                onInputChange = onSecondInputChange,
                keyboardActions = KeyboardActions(onDone = {
                    calculateBits(uiState.firstInput, uiState.secondInput)
                })
            )

            Button(
                onClick = {
                    calculateBits(uiState.firstInput, uiState.secondInput)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 55.dp, end = 55.dp, top = 32.dp)
                    .height(50.dp), shape = RoundedCornerShape(10.dp),
                enabled = uiState.isCalculateButtonEnabled(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text(
                    text = stringResource(id = (R.string.calculate)),
                    color = Color.Black
                )
            }

            CalculationResult(uiState = uiState)
        }
    }
}