package com.example.googlekeepclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import com.example.googlekeepclone.model.AuthViewModel
import com.example.googlekeepclone.navigation.Dashboard
import com.example.googlekeepclone.presentation.OnBoardingScreen
import com.example.googlekeepclone.ui.theme.GoogleKeepCloneTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalMaterialApi::class,
        ExperimentalFoundationApi::class,
        ExperimentalAnimationApi::class,
        ExperimentalCoroutinesApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GoogleKeepCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                   OnBoardingScreen(AuthViewModel())
//                    Dashboard()
                   
                }
            }
        }
    }
}




