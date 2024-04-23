package com.mariomanhique.quintadoeden.presentation.screens.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.model.Event
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    private val _items = MutableStateFlow<Map<LocalDate, List<Event>>>(emptyMap())
    val items = _items.asStateFlow()

    init {
        getEvents()
    }
    fun saveEvent(
        event: Event,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ){
        viewModelScope.launch {

            val result = firestoreRepository.saveEvent(event)
            if (result == "true"){
                Log.d("Result", "saveEvent: $result")
                onSuccess()
            }else{
                onError()
            }

        }
    }

    fun getEvents(){
        viewModelScope.launch {
            firestoreRepository.getEvents(
            ).distinctUntilChanged()
                .collectLatest {
                    _items.value = it
                }
        }
    }

}