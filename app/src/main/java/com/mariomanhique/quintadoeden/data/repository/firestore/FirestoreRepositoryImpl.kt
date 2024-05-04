package com.mariomanhique.quintadoeden.data.repository.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.mariomanhique.quintadoeden.model.Event
import com.mariomanhique.quintadoeden.model.Guest
import com.mariomanhique.quintadoeden.model.Note
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import com.mariomanhique.quintadoeden.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume

class FirestoreRepositoryImpl @Inject constructor(): FirestoreRepository {
    private var category: List<String> = emptyList()



   private lateinit var eventResult: String

    private val invCategories = FirebaseFirestore.getInstance()
        .collection("inventory")



    private val ref = FirebaseFirestore.getInstance()


    private val rooms = FirebaseFirestore.getInstance()
        .collection("events")



    override fun getInventoryProducts(): Flow<List<ProductInv>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): Flow<List<String>>{
        val document = invCategories.get().await()

        category = document.map {
            Log.d("Cat", "getCategories: ${it.id}")
            it.id
        }
        return flow { emit(category) }
    }

    override suspend fun getItemsByCategory(
        category: String,
        document: String
    ): Flow<List<ProductInv>> {
        return try {
            invCategories
                .document(document)
                .collection("subCollection")
                .document(category)
                .collection("lista")
                .snapshots()
                .map {snapshot->
                    Log.d("TAG", "getItemsByCategory:${snapshot.toObjects<ProductInv>()} ")
                    snapshot.toObjects<ProductInv>()
                }
        } catch (e: FirebaseFirestoreException){
            flowOf(emptyList())
        }
    }

    override suspend fun saveInventory(
        category: String,
        document: String,
        productInv: ProductInvToSave
    ){
        val result = invCategories
            .document(document)
            .collection("subCollection")
            .document(category)
            .collection("lista")
            .document(productInv.id)
            .set(productInv).isSuccessful
    }



    override suspend fun saveUpdateProductInv(
        category: String,
        document: String,
        productInv: ProductInvToSave
    ) {
        invCategories
            .document(document)
            .collection("subCollection")
            .document(category)
            .collection("lista")
            .document(productInv.id)
            .set(productInv)
    }

    override fun getGroupedDates(
        category: String,
        document: String
    ): Flow<Map<LocalDate, List<ProductInvToSave>>> {
       return invCategories
            .document(document)
            .collection("subCollection")
            .document(category)
            .collection("lista")
            .orderBy(
                "date",
                Query.Direction.DESCENDING
            ).snapshots().map {snapshot->
                snapshot.toObjects<ProductInvToSave>().run {
                    groupBy {
                          it.date
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                }
            }

    }

    override fun getInventoryByDates(
        category: String,
        localDate: LocalDate,
        document: String
    ): Flow<List<ProductInvToSave>> {

        val yesterday = localDate.minusDays(1)
        val tomorrow = localDate.plusDays(1)
        return invCategories
            .document(document)
            .collection("subCollection")
            .document(category)
            .collection("lista")
            .whereGreaterThan("date", Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant()))
            .whereLessThan("date", Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant()))
            .orderBy(
                "date",
                Query.Direction.DESCENDING
            ).snapshots().map {snapshot->
                snapshot.toObjects<ProductInvToSave>()
            }
    }

    override suspend fun saveEvent(event: Event):String {
        suspendCancellableCoroutine<Unit> { continuation ->
            ref.collection("events")
                .document(event.id)
                .set(event)
                .addOnSuccessListener {
                    eventResult = "true"
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    eventResult = "false"
                    continuation.resume(Unit)
                }
        }

        return eventResult
    }

    override fun getEvents(): Flow<Map<LocalDate, List<Event>>> {
        return ref
            .collection("events")
            .orderBy(
            "date",
            Query.Direction.DESCENDING
        ).snapshots().map {snapshot->
            snapshot.toObjects<Event>().run {
                groupBy {
                    it.date
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }
            }
        }
    }

    override fun getCompanyRooms(
        roomState: String,
        company: String
    ): Flow<List<Room>> {
        return ref
            .collection("rooms")
            .whereEqualTo("roomState",roomState)
            .whereEqualTo("company",company)
            .snapshots().map {
                it.toObjects<Room>()
            }
    }

    override fun getRooms(
        roomState: String,
    ): Flow<List<Room>> {
        return ref
            .collection("rooms")
            .whereEqualTo("roomState",roomState)
            .whereEqualTo("company","")
            .snapshots().map {
                it.toObjects<Room>()
            }
    }

    override fun getFilteredGuests(isCheckIn: Boolean, isCheckOut: Boolean): Flow<List<Guest>> {
        return ref
            .collection("guests")
            .whereEqualTo("isCheckIn", isCheckIn)
            .whereEqualTo("isCheckOut", isCheckOut)
            .snapshots().map {
                it.toObjects<Guest>()
            }
    }

    override fun getAllGuests(): Flow<List<Guest>> {
        return ref
            .collection("guests")
            .snapshots().map {
                it.toObjects<Guest>()
            }
    }

    override suspend fun editGuest(
        guest: Guest,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
       ref
        .collection("guests")
        .whereEqualTo("roomNr", guest.roomNr)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                ref.collection("guests")
                    .document(document.id)
                    .update(
                        mapOf(
                            "isCheckIn" to guest.isCheckIn,
                            "isCheckOut" to guest.isCheckOut
                        )
                    )
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onError()
                    }
            }
        }
        .addOnFailureListener { e ->
            onError()
        }
    }

    override suspend fun editRoomCleanState(
        roomState: String,
        roomNr: String,
        username: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
       ref
            .collection("rooms")
            .whereEqualTo("roomNr", roomNr)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    ref.collection("rooms").document(document.id)
                        .update(
                            mapOf(
                                "roomState" to roomState,
                                "username" to username
                            )
                        )
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                           onError()
                        }
                }
            }
            .addOnFailureListener { e ->
                onError()
            }

    }


    override suspend fun sendNote(
        note: Note,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        ref.collection("notes")
            .document(note.noteId)
            .set(note)
            .addOnSuccessListener {
               onSuccess()
            }.addOnFailureListener {
               onError()
            }
    }

    override fun getNotes(): Flow<List<Note>> {
        return ref
            .collection("notes")
            .orderBy(
                "noteDate",
                Query.Direction.ASCENDING)
            .snapshots().map {
                it.toObjects<Note>()
            }
    }

    override fun submitInv(category: String) {
        ref.collection("inventoryTrigger").document().set(
            hashMapOf(
                "category" to category,
                "date" to Date.from(
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
        )
    }
}