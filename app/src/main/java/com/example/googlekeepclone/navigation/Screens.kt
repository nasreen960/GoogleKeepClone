package com.example.googlekeepclone.navigation

sealed class Screens(
    val route:String
){
    object OnBoardingScreen : Screens(route = "onBoarding")
    object Dashboard : Screens(route = "Dashboard")
}
