package com.mariomanhique.quintadoeden.presentation.screens.inventory

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterMenuSheet(
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {

        OutlinedTextField(
            value = "",
            onValueChange = {}
        )

        OutlinedTextField(
            value = "",
            onValueChange = {}
        )

        OutlinedTextField(
            value = "",
            onValueChange = {}
        )
    }
}