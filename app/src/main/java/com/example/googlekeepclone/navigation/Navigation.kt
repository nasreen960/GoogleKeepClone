package com.example.googlekeepclone.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.googlekeepclone.model.AuthViewModel
import com.example.googlekeepclone.presentation.OnBoardingScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination =
        if (Firebase.auth.currentUser == null)
            Screens.OnBoardingScreen.route
        else
            Screens.Dashboard.route
    ) {
        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(authViewModel = AuthViewModel() )
        }
    }

}