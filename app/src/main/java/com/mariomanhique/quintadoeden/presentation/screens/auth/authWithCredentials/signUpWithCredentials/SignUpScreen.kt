package com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signUpWithCredentials


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import com.mariomanhique.quintadoeden.R
import com.mariomanhique.quintadoeden.presentation.components.EdenIcon
import com.mariomanhique.quintadoeden.presentation.components.GoogleButton
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.AuthWithCredentialsViewModel


@Composable
internal fun SignUpWithCredentials(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    isNetworkAvailable: Boolean,
    viewModel: AuthWithCredentialsViewModel = hiltViewModel(),
    navigateToHome:()->Unit,
    navigateToSignIn:()->Unit,
) {

    val isAuthenticated by viewModel.isAuthenticated
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    var textFieldEnabled by remember {
        mutableStateOf(true)
    }
    var nameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EdenIcon()

        OutlinedTextField(
            value = nameValue,
            enabled = isNetworkAvailable,
            onValueChange = { nameValue = it },
            label = { Text(text = "Usuário") },
            placeholder = { Text(text = "Usuário") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        OutlinedTextField(
            value = emailValue,
            enabled = isNetworkAvailable,
            onValueChange = { emailValue = it },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )


        OutlinedTextField(
            value = passwordValue,
            enabled = isNetworkAvailable,
            onValueChange = { passwordValue = it },
            label = { Text(text = "Palavra Passe") },
            placeholder = { Text(text = "Palavra Passe") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.password_eye),
                        contentDescription = "",
                        tint = if(passwordVisibility) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(10.dp))


        GoogleButton(
            primaryText = "Criar Conta",
            loadingState = isAuthenticated
        ) {
            if (emailValue.isNotEmpty() && passwordValue.isNotEmpty() && nameValue.isNotEmpty()) {
                viewModel.signUp(
                    username = nameValue,
                    email = emailValue,
                    password = passwordValue,
                    onSuccess = {
                        scope.launch {
                            onShowSnackbar("Usuário Registrado Com sucesso",null)
                        }
                        navigateToHome()
                        viewModel.resetAuthState()
                    },
                    onFailure = {
                        scope.launch {
                            onShowSnackbar("$it",null)
                            viewModel.resetAuthState()
                        }
                    }
                )
                textFieldEnabled = false
            } else{
                Toast.makeText(context,"Fields can't be blank", Toast.LENGTH_SHORT).show()
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Já tem conta? Entrar",
            modifier = Modifier.clickable(onClick = {
                navigateToSignIn()
            })
        )
        Spacer(modifier = Modifier.padding(20.dp))
    }
}