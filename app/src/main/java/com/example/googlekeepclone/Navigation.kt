package com.example.googlekeepclone.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.googlekeepclone.detail.DetailScreen
import com.example.googlekeepclone.detail.DetailViewModel
import com.example.googlekeepclone.home.Home
import com.example.googlekeepclone.home.HomeViewModel
import com.example.googlekeepclone.login.AuthViewModel
import com.example.googlekeepclone.login.SignUpLoginScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn



enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Home,
    Detail
}

enum class NestedRoutes{
    Main,
    Login
}


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: AuthViewModel,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if(GoogleSignIn.getLastSignedInAccount(LocalContext.current)==null){
            NestedRoutes.Login.name
        }else{
            NestedRoutes.Main.name

        }

    ) {
        authGraph(navController, loginViewModel)
        homeGraph(
            navController = navController,
            detailViewModel,
            homeViewModel
        )

    }


}

@RequiresApi(Build.VERSION_CODES.N)
fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: AuthViewModel,
){
    navigation(
        startDestination = LoginRoutes.SignIn.name,
        route = NestedRoutes.Login.name
    ){
        composable(route = LoginRoutes.SignIn.name) {
            SignUpLoginScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name) {
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.Signup.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(onNavToHomePage = {
                navController.navigate(NestedRoutes.Main.name) {
                    popUpTo(LoginRoutes.Signup.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }

        }

    }

}

@RequiresApi(Build.VERSION_CODES.N)
fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel,
    ){
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
    ){
        composable(HomeRoutes.Home.name){
            Home(
                homeViewModel = homeViewModel,
                onNoteClick = {noteId ->
                    navController.navigate(
                        HomeRoutes.Detail.name + "?id = $noteId"
                    ){
                        launchSingleTop = true
                    }

                },
                navToDetailPage = {
                    navController.navigate(HomeRoutes.Detail.name)
                }
            ) {
                navController.navigate(NestedRoutes.Login.name){
                    launchSingleTop = true
                    popUpTo(0){
                        inclusive = true
                    }
                }
                
            }
        }

        composable(
            route = HomeRoutes.Detail.name + "?id = {id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
                defaultValue = ""
            })
        ){entry->
            DetailScreen(detailViewModel = detailViewModel,
                noteId = entry.arguments?.getString("id") as String
            ) {
                navController.navigateUp()
                
            }

        }
    }

}