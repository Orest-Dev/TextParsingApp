package com.example.textparsingapp.ui.screen.text_parsing.view.components

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
import com.example.textparsingapp.R
import com.example.textparsingapp.ui.screen.text_parsing.state.TextParsingState
import com.example.textparsingapp.ui.screen.text_parsing.state.WordsResultState

@Composable
fun ParsingResult(uiState: TextParsingState) {
    when (uiState.wordsResultState) {
        is WordsResultState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 55.dp)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is WordsResultState.NoResult -> {
            Text(
                text = stringResource(id = (R.string.no_results)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 55.dp),
                textAlign = TextAlign.Center
            )
        }
        is WordsResultState.Error -> {
            Text(
                text = stringResource(id = (R.string.error_text)),
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 55.dp),
                textAlign = TextAlign.Center
            )
        }
        is WordsResultState.WordsResult -> {
            Text(
                text = uiState.wordsResultState.words,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 55.dp),
                textAlign = TextAlign.Center
            )
        }
        else -> {

        }
    }
}