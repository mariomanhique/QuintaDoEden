package com.mariomanhique.quintadoeden.presentation.screens.notes

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.presentation.components.InputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesSheet(
    onDismissRequest: () -> Unit,
    notesVieModel: NotesVieModel = hiltViewModel()
) {

    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp


    var note by remember {
        mutableStateOf("")
    }

    var maxLines by remember {
        mutableStateOf(Int.MAX_VALUE)
    }


    ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
        onDismissRequest = onDismissRequest,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    value = note,
                    placeholder = R.string.note,
                    isSingleLine = false,
                    maxLines = maxLines,
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    note = it
                }
                IconButton(
                    modifier = Modifier
                        .align(alignment = if (note.contains('\n')) Alignment.Bottom else Alignment.CenterVertically)
                        .size(screenWidth - (screenWidth.times(0.89F))),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = if (note.isNotEmpty()) Color(0XFF2cb747)else Color.Unspecified
                    ),
                    onClick = {
                    if (note.isNotEmpty()){
                        try {
                            notesVieModel.sendNote(
                                note = note,
                                onSuccess = {
                                   onDismissRequest()
                                },
                                onError = {}
                            )

                        } catch (e: FirebaseFirestoreException){
                            e.message
                        }
                    }
//                        val channel = NotificationChannel(
//                            "Novo Recado",
//                            "Quinda Do Eden Recados",
//                            NotificationManager.IMPORTANCE_HIGH
//                        )

//                       context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
//
//                        val notification = Notification.Builder(context,"Novo Recado")
//                            .setContentTitle("Title")
//                            .setContentText("New Note")
//                            .setSmallIcon(R.drawable.people)
//                            .setAutoCancel(true)
//
//                        if (ActivityCompat.checkSelfPermission(
//                                context,
//                                Manifest.permission.POST_NOTIFICATIONS
//                            ) != PackageManager.PERMISSION_GRANTED
//                        ) {
//                            return@IconButton
//                        }
//                        NotificationManagerCompat.from(context).notify(1,notification.build())
                }) {
                    Icon(
                        modifier = Modifier
                            .size(screenWidth - (screenWidth.times(0.9F)))
                            .padding(5.dp),
                        imageVector = Icons.Filled.Send,
                        contentDescription = "",
                        tint =  if (note.isNotEmpty()) Color.White else Color.Unspecified
                    )
                }
            }
        }
    }
}

