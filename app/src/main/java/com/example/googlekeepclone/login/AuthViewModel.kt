package com.example.googlekeepclone.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel: ViewModel() {
    private val _user:MutableStateFlow<User?> = MutableStateFlow(null)
   val user:StateFlow<User?> = _user
     fun signIn(email:String, displayName:String){
         _user.value = User(email,displayName)
     }
}