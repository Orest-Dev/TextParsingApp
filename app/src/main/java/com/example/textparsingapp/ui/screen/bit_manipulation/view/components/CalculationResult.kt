package com.example.textparsingapp.ui.screen.bit_manipulation.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textparsingapp.R
import com.example.textparsingapp.ui.screen.bit_manipulation.state.BitManipulationState
import com.example.textparsingapp.ui.screen.bit_manipulation.state.BitsResultState

@Composable
fun CalculationResult(uiState: BitManipulationState) {
    when (uiState.bitsResultState) {
        is BitsResultState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is BitsResultState.Error -> {
            Text(
                text = stringResource(id = (R.string.error_text)),
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 45.dp),
                textAlign = TextAlign.Center
            )
        }

        is BitsResultState.BitsResult -> {
            Text(
                text = uiState.bitsResultState.bits,
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 45.dp),
                textAlign = TextAlign.Center
            )
        }

        else -> {
            //showing nothing
        }
    }
}