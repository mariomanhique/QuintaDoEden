package com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.quintadoeden.data.repository.authWithCredentials.AuthRepository
import com.mariomanhique.quintadoeden.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class AuthWithCredentialsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
):ViewModel() {

    var isAuthenticated = mutableStateOf(false)

    fun resetAuthState(){
        isAuthenticated.value = false
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit

    ){
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()){
                isAuthenticated.value = false
            }else {
                authRepository.signIn(
                    email = email,
                    password = password
                ).also {
                    when (it) {
                        is Result.Success -> {
                            onSuccess()
                            isAuthenticated.value = true
                        }

                        is Result.Loading -> {
                            isAuthenticated.value = false
                        }

                        is Result.Error -> {
                            it.exception?.let { exception -> onFailure(exception) }
                        }
                    }
                }
            }



        }
    }

    fun signUp(
        email: String,
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ){
        viewModelScope.launch {
            //This check is done also on the screens that use the method, but I had to do it here for unit tests
            if (email.isEmpty() || username.isEmpty() || password.isEmpty()){
                isAuthenticated.value = false
            }else{
                isAuthenticated.value = true
                authRepository.signUp(
                    email = email,
                    username = username,
                    password = password
                ).also {
                    when(it){
                        is Result.Success ->{
                            onSuccess()
                            isAuthenticated.value = true
                        }
                        is Result.Loading -> {
                            isAuthenticated.value = false
                        }

                        is Result.Error -> {
                            it.exception?.let { exception -> onFailure(exception) }
                        }
                    }
                }
            }
        }
    }
}