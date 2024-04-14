package com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.signInWithCredencials



import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.quintadoeden.presentation.screens.auth.authWithCredentials.AuthWithCredentialsViewModel
import com.mariomanhique.quintadoeden.presentation.components.GoogleButton
import com.mariomanhique.quintadoeden.R
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navigateToHome: ()->Unit,
    navigateToSignUp:()->Unit,
    isNetworkAvailable: Boolean,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: AuthWithCredentialsViewModel = hiltViewModel(),
) {

    val isAuthenticated by viewModel.isAuthenticated
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.diary),
//            contentDescription = "logo"
//        )
        Text(
            modifier = Modifier.paddingFromBaseline(top =50.dp, bottom = 50.dp),
            text = "Sign In",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            fontSize = 30.sp
        )
        OutlinedTextField(
            value = emailValue,
            enabled = isNetworkAvailable,
            onValueChange = {
                emailValue = it },
            label = { Text(text = "Email Address") },
            placeholder = { Text(text = "Email Address") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .focusRequester(focusRequester = focusRequester),
        )

        OutlinedTextField(
            value = passwordValue,
            enabled = isNetworkAvailable,
            onValueChange = { passwordValue = it },
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
            label = { Text("Password") },
            placeholder = { Text(text = "Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .focusRequester(focusRequester = focusRequester),
        )

        Spacer(modifier = Modifier.padding(10.dp))

        GoogleButton(loadingState = isAuthenticated) {

            if(emailValue.isNotEmpty() && passwordValue.isNotEmpty()){
                viewModel.signIn(
                    email = emailValue,
                    password = passwordValue,
                    onSuccess = {
                        scope.launch {
                            onShowSnackbar("Logged In Successfully",null)
                            navigateToHome()
                            viewModel.resetAuthState()
                        }
                    },
                    onFailure = {
                        scope.launch {
                            onShowSnackbar(it.message.toString(),null)
                        }
                    }
                )
            }else{
                Toast.makeText(context,"Fields can't be blank", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Create An Account",
            modifier = Modifier.clickable(onClick = {
                navigateToSignUp()
            })
        )
        Spacer(modifier = Modifier.padding(20.dp))
    }
}
