package com.mariomanhique.quintadoeden.presentation.screens.notes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.presentation.components.TopBar
import java.text.SimpleDateFormat
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mariomanhique.quintadoeden.model.Note
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notesVieModel: NotesVieModel = hiltViewModel()

) {

    val notes by notesVieModel.items.collectAsStateWithLifecycle()

    var sheetState by remember {
        mutableStateOf(false)
    }

    var message by remember {
        mutableStateOf("")
    }




    Column(
        modifier =  Modifier
            .fillMaxSize(),
//        verticalArrangement = Arra

    ) {
        TopBar(
            title = stringResource(id = R.string.notes),
            textAction = "Novo Recado",
            onTextActionClicked = {
                sheetState = true
            },
            popBackStack = {}
        )
        NotesContent(notes)

        MessageCard(
            message = message,
            onMessageChange = {
                message = it
            },
            onSendClicked = {
                if (message.isNotEmpty()){
                    try {
                        notesVieModel.sendNote(
                            note = message,
                            onSuccess = {

                            },
                            onError = {}
                        )

                    } catch (e: FirebaseFirestoreException){
                        e.message
                    }
                }
            }
        )
    }
}

@Composable
fun NotesContent(
    notes: List<Note>
) {
    NotesLazyList(
        notes = notes
    )
}


@Composable
fun NotesLazyList(
    notes: List<Note>
) {

    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = notes.count(),
    )

    LaunchedEffect(Unit) {
        scrollState.scrollToItem(notes.count())
    }

    Log.d("Count", "NotesLazyList: ${notes.count()} ${notes.lastIndex} ")

    LazyColumn(
        state = scrollState,
        reverseLayout = false
    ) {
        items(items = notes){
            NoteCard(text =it.text , username = it.username, dateTime = it.noteDate)
        }
    }
}

@Composable
fun NoteCard(
    text: String,
    username: String,
    dateTime: Date
) {


    val dateFormat = SimpleDateFormat.getDateTimeInstance()
    val formattedDate = dateFormat.format(dateTime)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = username,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            )

//        Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = text,
                maxLines = Int.MAX_VALUE,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                   // color = MaterialTheme.colorScheme.secondary
                )
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = formattedDate,
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.secondary

            )
        }
    }
}