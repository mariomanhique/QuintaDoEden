package com.mariomanhique.quintadoeden.presentation.screens.inventory.submitted

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.quintadoeden.Util.Constants
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SubmittedInventoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val firestoreRepository: FirestoreRepository

) :ViewModel() {

    private val _items = MutableStateFlow<Map<LocalDate, List<ProductInvToSave>>>(emptyMap())
    val items = _items.asStateFlow()

    val category = savedStateHandle.get<String>(
        key = Constants.CATEGORY_ARG
    )

    init {
        if (category != null) {
            getDates(category.lowercase())
        }
    }


    fun getDates(
        category: String
    ){
        viewModelScope.launch {
            firestoreRepository.getGroupedDates(
                category
            ).distinctUntilChanged()
                .collectLatest {
                    _items.value = it
                }
        }
    }






}