package com.mariomanhique.quintadoeden.presentation.screens.rooms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.model.Guest
import com.mariomanhique.quintadoeden.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _guests = MutableStateFlow<List<Guest>>(emptyList())
    val guests = _guests.asStateFlow()

    private val _outGuests = MutableStateFlow<List<Guest>>(emptyList())
    val outGuests = _outGuests.asStateFlow()

    private val _inGuests = MutableStateFlow<List<Guest>>(emptyList())
    val inGuests = _inGuests.asStateFlow()

    private val _unCleanedRooms = MutableStateFlow<List<Room>>(emptyList())
    val unCleanedRooms = _unCleanedRooms.asStateFlow()

    init {
        getRooms()
        //getGuests()
        getAllGuests()
        getFilteredGuests()
        getFilteredOutGuests()
        getUncleanedRooms()
    }

    fun getRooms(roomState: String = "Limpo"){
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository
                .getRooms(roomState)
                .distinctUntilChanged()
                .collectLatest {
                    _items.value = it
                }
        }
    }


    fun getFilteredGuests(
        isCheckIn: Boolean = true,
        isCheckOut: Boolean = false,
    ){
        viewModelScope.launch {
            firestoreRepository.getFilteredGuests(
                isCheckIn = isCheckIn,
                isCheckOut = isCheckOut,
            ).distinctUntilChanged()
                .collectLatest {
                    _inGuests.value = it
                }
        }
    }

    fun getFilteredOutGuests(
        isCheckIn: Boolean = false,
        isCheckOut: Boolean = true,
    ){
        viewModelScope.launch {
            firestoreRepository.getFilteredGuests(
                isCheckIn = isCheckIn,
                isCheckOut = isCheckOut,
            ).distinctUntilChanged()
                .collectLatest {
                    _outGuests.value = it
                }
        }
    }

    fun getAllGuests(){
        viewModelScope.launch {
            firestoreRepository.getAllGuests(
            ).distinctUntilChanged()
                .collectLatest {
                    _guests.value = it
                    Log.d("ViewModel", "getAllGuests: $it ")
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
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                firestoreRepository.editRoomCleanState(
                    roomState = roomState,
                    roomNr = roomNr,
                    username = user.displayName.toString(),
                    onSuccess = onSuccess,
                    onError = onError
                )
            }

        }
    }

    fun editGuests(
        guest: Guest,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ){
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                firestoreRepository.editGuest(
                    guest = guest,
                    onSuccess = onSuccess,
                    onError = onError
                )
            }

        }
    }
}