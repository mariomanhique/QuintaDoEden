package com.mariomanhique.quintadoeden.presentation.screens.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    private val _items = MutableStateFlow<List<Room>>(emptyList())
    val items = _items.asStateFlow()

    private val _unCleanedRooms = MutableStateFlow<List<Room>>(emptyList())
    val unCleanedRooms = _unCleanedRooms.asStateFlow()

    init {
        getRooms()
        getUncleanedRooms()
    }


    fun getRooms(roomState: String = "Limpo"){
        viewModelScope.launch {
            firestoreRepository
                .getRooms(roomState)
                .distinctUntilChanged()
                .collectLatest {
                    _items.value = it
                }
        }
    }

    fun getUncleanedRooms(roomState: String = "Sujo"){
        viewModelScope.launch {
            firestoreRepository
                .getRooms(roomState)
                .distinctUntilChanged()
                .collectLatest {
                    _unCleanedRooms.value = it
                }
        }
    }

    fun editRoomCleanState(
        roomState: String,
        roomNr: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ){
        viewModelScope.launch {
            firestoreRepository.editRoomCleanState(
                roomState = roomState,
                roomNr = roomNr,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

}