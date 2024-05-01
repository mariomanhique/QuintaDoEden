package com.mariomanhique.quintadoeden.data.repository.firestore

import com.mariomanhique.quintadoeden.model.Event
import com.mariomanhique.quintadoeden.model.Note
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.model.Room
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface FirestoreRepository {

    fun getInventoryProducts(): Flow<List<ProductInv>>

    suspend fun getCategories(): Flow<List<String>>

    suspend fun getItemsByCategory(
        category: String,
        document: String
    ):Flow<List<ProductInv>>

    suspend fun saveInventory(
        category: String,
        document: String,
        productInv: ProductInvToSave
    )

    suspend fun saveUpdateProductInv(
        category: String,
        document: String,
        productInv: ProductInvToSave
    )

    fun getGroupedDates(
        category: String,
        document: String
    ): Flow<Map<LocalDate, List<ProductInvToSave>>>

    fun getInventoryByDates(
        category: String,
        localDate: LocalDate,
        document: String
    ): Flow<List<ProductInvToSave>>

    suspend fun saveEvent(
        event: Event
    ): String

    fun getEvents(): Flow<Map<LocalDate, List<Event>>>

    fun getRooms(roomState: String): Flow<List<Room>>

    suspend fun editRoomCleanState(
        roomState: String,
        roomNr: String,
        username: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    )

    suspend fun sendNote(
        note: Note,
        onSuccess: () -> Unit,
        onError: () -> Unit
    )

    fun getNotes(): Flow<List<Note>>

    fun submitInv(
        category: String
    )

}