package com.mariomanhique.quintadoeden.presentation.screens.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    private var _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        getCategories()

    }

    private fun getCategories(){
        viewModelScope.launch {
            firestoreRepository
                .getCategories().distinctUntilChanged()
                .collectLatest {
                    _categories.value = it
                }

        }
    }
}