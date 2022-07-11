package com.example.googlekeepclone.presentation
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import com.example.googlekeepclone.OnboardingScreen
import com.example.googlekeepclone.model.AuthViewModel
import com.example.googlekeepclone.utils.AuthResultContract
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

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
                        account.email?.let {
                            account.displayName?.let { text ->
                                authViewModel.updateUser(
                                    email = it,
                                    displayName = text) } }
                    }
                }
            }catch (e:ApiException){
                text = "Google sign In Failed"
            }

        }
    OnboardingScreen(errorText = text, onClick = {text = null
    authResultLauncher.launch(signInRequestCode)})

    user?.let{
        HomeScreen(user = it)
    }

}