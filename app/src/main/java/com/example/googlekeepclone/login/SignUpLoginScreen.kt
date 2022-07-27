package com.example.googlekeepclone.login

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.googlekeepclone.R
import com.example.googlekeepclone.home.Home
import com.example.googlekeepclone.home.HomeViewModel
import com.example.googlekeepclone.navigation.NestedRoutes
import com.example.googlekeepclone.ui.theme.ButtonBlue
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SignUpLoginScreen(userAuthViewModel:AuthViewModel,
navController: NavHostController){
    var errorText : String? by remember{ mutableStateOf(null)}
    val coroutineScope = rememberCoroutineScope()
    var text by remember{ mutableStateOf<String>("")}
    val signInRequestCode = 1

    val userResultLauncher =
        rememberLauncherForActivityResult(contract = UserResultContract()){
                task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account==null){
                    text = "Google sign in failed"
                }else{
                    coroutineScope.launch {
                        account.email?.let {email-> account.displayName?.let { displayName -> userAuthViewModel.signIn(email = email, displayName = displayName) } }
                      navController.navigate(NestedRoutes.Main.name)
                    }
                }
            }catch (e: ApiException){
                text = "Google Sign In Failed"
            }

        }
    Scaffold {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.con),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(80.dp))
        Text(
            text = "Capture anything",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = "Make lists,take photos, speak your mind-whatever works for you, works in Keep",
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.width(40.dp))
        GoogleSignInButton(text = "Sign in with Google",
            loadingText = "Signing In",
            onClicked = {text = ""
            userResultLauncher.launch(signInRequestCode)})
        errorText?.let {
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = it)

        }
    }
    }
}








@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleSignInButton(
    text:String = " ",
    loadingText: String = " ",
   onClicked:()->Unit
){
    var clicked by remember{mutableStateOf(false)}
Surface(
    onClick = {clicked = !clicked},
    shape = CircleShape,
    color = ButtonBlue
) {
    Row(modifier = Modifier
        .padding(
            horizontal = 12.dp,
            vertical = 16.dp
        )
        .animateContentSize(
            animationSpec = tween(300, easing = LinearOutSlowInEasing)
        ), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(text = if(clicked)loadingText else text)
        if(clicked){
            Spacer(modifier = Modifier.width(16.dp))
            CircularProgressIndicator(
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colors.primary
            )
            onClicked()
            
        }

        
    }
        
    }

}
