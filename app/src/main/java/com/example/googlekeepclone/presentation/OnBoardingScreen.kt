package com.example.googlekeepclone

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googlekeepclone.ui.theme.ButtonBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UI(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(40.dp)) {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Google keep Icon",
            modifier = Modifier
                .height(300.dp)
                .width(160.dp))
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Capture anything",
            fontSize = 24.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Make lists, take photos, speak your mind-whatever works for you, works in Keep.",
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(40.dp))
    }

}
@ExperimentalMaterialApi
@Composable
fun GoogleSignInButton(text:String = "", loadingText:String = "",
onClicked:()->Unit){
    var clicked by remember{ mutableStateOf(false)}
   Surface(
       onClick = {clicked = !clicked },
       shape = RoundedCornerShape(50),
       modifier = Modifier
           .width(200.dp)
           .height(60.dp),
       color = ButtonBlue
       ) {
       Row(verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Center,
       modifier = Modifier.animateContentSize(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)))
       {
           Text(
               text = if (clicked) loadingText else text
           )

           if (clicked){
               CircularProgressIndicator(
                   modifier = Modifier.size(16.dp),
                   strokeWidth = 2.dp,
                   color = MaterialTheme.colors.primary
               )
               onClicked()
           }
       }
    }
}

@Composable
@Preview
fun PreviewThis(){
    UI()
}