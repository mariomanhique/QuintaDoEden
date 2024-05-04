package com.mariomanhique.quintadoeden.presentation.screens.companiesRooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
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
class CompaniesRoomsViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
): ViewModel() {


    private val _unCleanedRooms = MutableStateFlow<List<Room>>(emptyList())
    val unCleanedRooms = _unCleanedRooms.asStateFlow()

    private val _cleanedRooms = MutableStateFlow<List<Room>>(emptyList())
    val cleanedRooms = _cleanedRooms.asStateFlow()


    init {
        getCompanyCleanRooms()
        getCompanyUnCleanRooms()
    }

    fun getCompanyUnCleanRooms(
        roomState: String = "Limpo",
        company: String = "RODOPI"
    ){
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository
                .getCompanyRooms(
                    roomState =  roomState,
                    company = company
                )
                .distinctUntilChanged()
                .collectLatest {
                    _cleanedRooms.value = it
                }
        }
    }

    fun getCompanyCleanRooms(
        roomState: String = "Sujo",
        company: String = "RODOPI"
    ){
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository
                .getCompanyRooms(
                    roomState =  roomState,
                    company = company
                )
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

}