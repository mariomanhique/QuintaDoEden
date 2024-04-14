package com.mariomanhique.quintadoeden.data.repository.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.mariomanhique.quintadoeden.model.ProductInv
import com.mariomanhique.quintadoeden.model.ProductInvToSave
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(): FirestoreRepository {
    private var category: List<String> = emptyList()
    private lateinit var date: LocalDate

    private val invCategories = FirebaseFirestore.getInstance()
        .collection("inventory")
        .document("products")
        .collection("subCollection")

    private val submitInv = FirebaseFirestore.getInstance()
        .collection("inventory")
        .document("inventories")
        .collection("subCollection")



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

    override suspend fun getItemsByCategory(category: String): Flow<List<ProductInv>> {
        return try {
            invCategories
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
                 productInv: ProductInvToSave
    ){
        submitInv
            .document(category)
            .collection("lista")
            .document(productInv.id)
            .set(productInv)
    }

    override suspend fun saveUpdateProductInv(
        category: String,
        productInv: ProductInvToSave
    ) {
        invCategories.document(category)
            .collection("lista")
            .document(productInv.id)
            .set(productInv)
    }

    override fun getGroupedDates(category: String): Flow<Map<LocalDate, List<ProductInvToSave>>> {
       return submitInv
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
        zonedDateTime: ZonedDateTime
    ): Flow<List<ProductInvToSave>> {
        return submitInv
            .document(category)
            .collection("lista")
            .whereGreaterThan("date", Date.from(zonedDateTime.minusDays(1).toInstant()))
            .whereLessThan("date", Date.from(zonedDateTime.plusDays(1).toInstant()))
            .orderBy(
                "date",
                Query.Direction.DESCENDING
            ).snapshots().map {snapshot->
                snapshot.toObjects<ProductInvToSave>()
            }
    }
}