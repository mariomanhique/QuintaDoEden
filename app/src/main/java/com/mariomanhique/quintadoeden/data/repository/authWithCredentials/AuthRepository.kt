package com.mariomanhique.quintadoeden.data.repository.authWithCredentials
import com.mariomanhique.quintadoeden.model.Result


interface AuthRepository {
    suspend fun signIn(email:String,password:String): Result<Boolean>
    suspend fun signUp(email:String,username:String,password:String): Result<Boolean>

}