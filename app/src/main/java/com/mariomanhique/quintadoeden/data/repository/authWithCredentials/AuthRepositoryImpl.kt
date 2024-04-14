package com.mariomanhique.quintadoeden.data.repository.authWithCredentials


import com.mariomanhique.quintadoeden.data.repository.authWithCredentials.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.mariomanhique.quintadoeden.model.User
import javax.inject.Inject
import com.mariomanhique.quintadoeden.model.Result


class AuthRepositoryImpl @Inject constructor(
): AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<Boolean> {
        val firebaseAuth = FirebaseAuth.getInstance()

        return try {
            firebaseAuth.signInWithEmailAndPassword(email,password).await().user
            Result.Success(true)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun signUp(email: String, username: String, password: String): Result<Boolean> {
        val  firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance().collection("profile")

        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await().user
            if  (result != null){
                result.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(username).build())

                result.email?.let {userEmail->
                    firestore.document(result.uid).set(
                        User(
                            userId = result.uid,
                            email = userEmail,
                            username = username,
                            profilePictureUrl = ""
                        )
                    )
                }

                Result.Success(true)
            } else{
                Result.Success(false)
            }

        }catch (e: Exception){
            Result.Error(e)
        }

    }

}