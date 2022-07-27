package com.example.googlekeepclone.model

import com.google.firebase.Timestamp


data class Notes(
    val userId: String = "",
    val title: String=" ",
    val description: String ="",
    val timestamp: Timestamp = Timestamp.now(),
    val documentId: String = ""
)