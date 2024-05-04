package com.mariomanhique.quintadoeden.presentation.components

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp


@Composable
fun InputField(
    modifier: Modifier = Modifier,
    maxLines: Int,
    isSingleLine: Boolean,
    scrollState: ScrollState?=null,
    shape: Shape,
    value: String,
    trailingIcon:  @Composable() (() -> Unit)? = null,
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .focusRequester(focusRequester = focusRequester),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(text = stringResource(id = placeholder))
        },
        trailingIcon = trailingIcon,
        shape = shape,
        maxLines = maxLines,
        singleLine = isSingleLine
    )
}