package com.example.textparsingapp.ui.screen.text_parsing.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textparsingapp.R
import com.example.textparsingapp.ui.screen.text_parsing.state.TextParsingState

@Composable
fun InputTextBox(
    uiState: TextParsingState,
    parseText: (query: String) -> Unit,
    validate: (query: String) -> Unit
) {
    val shape = RoundedCornerShape(percent = 40)
    Box(
        modifier = Modifier
            .padding(all = 16.dp)
            .shadow(
                elevation = 5.dp,
                shape = shape
            )
            .background(color = Color.White, shape = shape)
            .fillMaxWidth()
            .height(50.dp),
    ) {
        BasicTextField(
            value = uiState.input,
            onValueChange = { enteredText -> validate(enteredText) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                parseText(uiState.input)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = shape
                )
                .padding(start = 14.dp, end = 55.dp, top = 12.dp, bottom = 12.dp),
            textStyle = TextStyle(color = MaterialTheme.colors.secondary, fontSize = 17.sp),
            cursorBrush = SolidColor(MaterialTheme.colors.secondary),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (uiState.input.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.enter_text),
                            color = MaterialTheme.colors.secondary,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.body2
                        )
                    }

                    innerTextField()
                }
            },
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "",
            modifier = Modifier
                .size(36.dp)
                .align(alignment = Alignment.CenterEnd)
                .padding(end = 12.dp),
        )
    }
}