package com.example.textparsingapp.ui.screen.bit_manipulation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textparsingapp.R

@Composable
fun InputNumberBox(
    inputNumber: String,
    onInputChange: (query: String) -> Unit,
    keyboardActions: KeyboardActions
) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(percent = 40)
            )
            .background(color = Color.White, shape = RoundedCornerShape(percent = 40))
            .height(50.dp)
            .fillMaxWidth(),
    ) {
        BasicTextField(
            value = inputNumber,
            onValueChange = { value ->
                onInputChange(value)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = keyboardActions,
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(percent = 40)
                )
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = MaterialTheme.colors.secondary,
                fontSize = 17.sp
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.secondary),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (inputNumber.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.enter_number),
                            color = MaterialTheme.colors.secondary,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.body1
                        )
                    }

                    innerTextField()
                }
            },
        )
    }
}