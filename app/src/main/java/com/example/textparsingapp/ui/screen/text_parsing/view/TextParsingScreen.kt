package com.example.textparsingapp.ui.screen.text_parsing.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.textparsingapp.ui.screen.text_parsing.state.TextParsingState
import com.example.textparsingapp.ui.screen.text_parsing.view.components.InputTextBox
import com.example.textparsingapp.ui.screen.text_parsing.view.components.ParsingResult

@Composable
fun TextParsingRoute(
    viewModel: TextParsingViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    TextParsingScreen(
        uiState = uiState,
        parseText = viewModel::parseText,
        validate = viewModel::validate,
    )
}

@Composable
fun TextParsingScreen(
    uiState: TextParsingState,
    parseText: (query: String) -> Unit,
    validate: (query: String) -> Unit
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
                text = stringResource(id = (R.string.search_text)),
                color = Color.Black,
                modifier = Modifier.padding(all = 16.dp),
                textAlign = TextAlign.Center
            )
            InputTextBox(uiState = uiState, parseText = parseText, validate = validate)

            Button(
                onClick = { parseText(uiState.input) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 55.dp, end = 55.dp, top = 25.dp)
                    .height(50.dp), shape = RoundedCornerShape(10.dp),
                enabled = uiState.isParseButtonEnabled(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text(text = stringResource(id = (R.string.parse)), color = Color.Black)
            }

            ParsingResult(uiState = uiState)
        }
    }
}