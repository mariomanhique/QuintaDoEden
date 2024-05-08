package com.mariomanhique.quintadoeden.presentation.screens.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.presentation.components.InputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageCard(
    onSendClicked: () -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    maxLines: Int = Int.MAX_VALUE,
) {


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy((-15).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputField(
                modifier = Modifier
                    .width(screenWidth - (screenWidth.times(0.1F))),
                value = message,
                placeholder = R.string.note,
                isSingleLine = false,
                maxLines = maxLines,
                shape = MaterialTheme.shapes.extraLarge
            ) {
                onMessageChange(it)
            }
            IconButton(
                modifier = Modifier
                    .align(alignment = if (message.contains('\n')) Alignment.Bottom else Alignment.CenterVertically)
                    .size(screenWidth - (screenWidth.times(0.89F))),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = if (message.isNotEmpty()) Color(0XFF2cb747)else Color.Unspecified
                ),
                onClick = {
                    onSendClicked()
            }) {
                Icon(
                    modifier = Modifier
                        .size(screenWidth - (screenWidth.times(0.9F)))
                        .padding(5.dp),
                    imageVector = Icons.Filled.Send,
                    contentDescription = "",
                    tint =  if (message.isNotEmpty()) Color.White else Color.Unspecified
                )
            }
        }
    }
}

