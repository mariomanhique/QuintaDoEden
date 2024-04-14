package com.mariomanhique.quintadoeden.data.repository.firestore

import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZonedDateTime

interface FirestoreRepository {

    fun getInventoryProducts(): Flow<List<ProductInv>>

    suspend fun getCategories(): Flow<List<String>>

    suspend fun getItemsByCategory(
        category: String,
    ):Flow<List<ProductInv>>

    suspend fun saveInventory(
        category: String,
        productInv: ProductInvToSave
    )

    suspend fun saveUpdateProductInv(
        category: String,
        productInv: ProductInvToSave
    )

    fun getGroupedDates(
        category: String,
    ): Flow<Map<LocalDate, List<ProductInvToSave>>>

    fun getInventoryByDates(
        category: String,
        zonedDateTime: ZonedDateTime
    ): Flow<List<ProductInvToSave>>



}