package com.mariomanhique.quintadoeden.presentation.screens.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.data.repository.network.NetworkRepository
import com.mariomanhique.quintadoeden.model.Note
import com.mariomanhique.quintadoeden.sync.NoteState
import com.mariomanhique.quintadoeden.sync.NotificationBody
import com.mariomanhique.quintadoeden.sync.SendMessageDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NotesVieModel @Inject constructor(
private val firestoreRepository: FirestoreRepository,
//private val networkRepository: NetworkRepository
): ViewModel() {

//    var state by mutableStateOf(NoteState())
//        private set

    private val _items = MutableStateFlow<List<Note>>(emptyList())
    val items = _items.asStateFlow()

    init {
        getNotes()
    }

//    fun onRemoteTokenChange(newToken: String){
//        state = state.copy(
//            remoteToken = newToken
//        )
//    }

//    fun onSubmitToken(){
//        state = state.copy(
//            isEnteringToken = false
//        )
//    }
//
//    fun onMessageChange(message: String){
//        state = state.copy(
//            messageText = message
//        )
//    }

//    fun sendMessageNotification(
//        message: String,
//        isBroadcast: Boolean
//    ){
//        viewModelScope.launch {
//           val messageDto = SendMessageDto(
//                to = if (isBroadcast) null else state.remoteToken,
//                notificationBody = NotificationBody(
//                    title = "Novo Recado!",
//                    body = message
//                ))
//            try {
//                if (isBroadcast) {
//                    networkRepository.broadcast(messageDto)
//                } else{
//                    networkRepository.sendMessage(messageDto)
//                }
//                state = state.copy(messageText = "")
//            } catch (e: HttpException){
//                e.printStackTrace()
//            } catch (io: IOException){
//                io.printStackTrace()
//            }
//        }
//    }

    fun getNotes(){
        viewModelScope.launch {
            firestoreRepository
                .getNotes()
                .distinctUntilChanged()
                .collectLatest {
                    _items.value = it
                }
        }
    }

    fun sendNote(
        note: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ){
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                firestoreRepository.sendNote(
                    Note(
                        username = user.displayName.toString(),
                        text = note
                    ),
                    onSuccess = {
                        onSuccess()
                    },
                    onError = onError
                )
            }
        }
    }
}