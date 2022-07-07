package com.example.googlekeepclone.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.googlekeepclone.GoogleSignInButton
import com.example.googlekeepclone.UI
import com.example.googlekeepclone.model.AuthViewModel
import com.example.googlekeepclone.utils.AuthResultContract
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthView(errorText:String?,
             onClick:()->Unit
){
    Scaffold {
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            UI()
            GoogleSignInButton(text = "Sign in With Google",
            loadingText = "Signing In.....",
            onClicked = {onClick()})
            errorText?.let{
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = it)
            }
        }
    }
}
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun AuthScreen(authViewModel: AuthViewModel){
    val coroutineScope = rememberCoroutineScope()
    var text by remember{ mutableStateOf<String?>(null)}
    val user by remember(authViewModel){authViewModel.user}.collectAsState()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract() ){task->
            try{
                val account = task?.getResult(ApiException::class.java)
                if(account==null){
                    text = "Google sign in failed"
                }
                else{
                    coroutineScope.launch {
                        account.email?.let { account.displayName?.let { it1 -> authViewModel.signIn(email = it, displayName = it1) } }
                    }
                }
            }catch (e:ApiException){
                text = "Google sign In Failed"
            }

        }
    AuthView(errorText = text, onClick = {text = null
    authResultLauncher.launch(signInRequestCode)})

    user?.let{
        HomeScreen(user = it)
    }

}