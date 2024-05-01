package com.mariomanhique.quintadoeden.presentation.screens.inventory

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.quintadoeden.util.Constants.CATEGORY_ARG
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillInventoryViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _items = MutableStateFlow<List<ProductInv>>(emptyList())
    val items = _items.asStateFlow()

    val category = savedStateHandle.get<String>(
        key = CATEGORY_ARG)
    init {
        Log.d("ViewModel", "ViewModel: $category")

        getItemsByCategory(category = category)
    }

    fun getItemsByCategory(
        category: String?
    ){
        viewModelScope.launch {
            if (category != null){
                firestoreRepository.getItemsByCategory(
                    category = category,
                    document = "products"
                )
                    .distinctUntilChanged()
                    .collectLatest {
                        Log.d("Category", "getItemsByCategory: $it ")
                        _items.value = it
                    }
            }
            Log.d("Category", "getItemsByCategory: Null ")
        }
    }

    fun saveInventory(
        productInv: ProductInvToSave,
        document: String = "inventories"
    ){
        viewModelScope.launch {
            if (category != null) {
                firestoreRepository.saveInventory(
                    category = category,
                    productInv = productInv,
                    document = document
                )
            }
        }
    }

    fun submitInv(){
        viewModelScope.launch {
            if (category != null) {
               firestoreRepository.submitInv(category)
            }
        }
    }


}