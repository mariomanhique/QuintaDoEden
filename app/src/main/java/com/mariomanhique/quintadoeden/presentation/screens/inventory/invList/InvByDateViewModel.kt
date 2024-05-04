package com.mariomanhique.quintadoeden.presentation.screens.inventory.invList

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.quintadoeden.util.Constants.LOCAL_DATE
import com.mariomanhique.quintadoeden.util.dateParser
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvByDateViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    private val _items = MutableStateFlow<List<ProductInvToSave>>(emptyList())
    val items = _items.asStateFlow()

    val dateCategory = savedStateHandle.get<String>(
        key = LOCAL_DATE
    )
    init {
        val list = dateCategory?.split("&")

        if (list != null){
            val date = list[0]
            val category = list[1]

         //   Log.d(TAG, ": ")
            getInvs(
                category = category,
                localDate = date
            )
        }
    }

    private fun getInvs(
        category: String,
        localDate: String,
    ){
        viewModelScope.launch {
            firestoreRepository.getInventoryByDates(
                category,
                dateParser(localDate),
                "inventories"
            ).distinctUntilChanged()
                .collectLatest {
                    _items.value = it
                }
        }
    }
}