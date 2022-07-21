package com.example.googlekeepclone.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    noteId: String,
    onNavigate: () -> Unit
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()

    val isNoteIdNotBlank = noteId.isNotBlank()
    val icon = if (isNoteIdNotBlank) Icons.Default.Refresh
    else Icons.Default.Check
    LaunchedEffect(key1 = Unit) {
        if (isNoteIdNotBlank) {
            detailViewModel?.getNote(noteId)
        } else {
            detailViewModel?.resetState()
        }
    }
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isNoteIdNotBlank) {
                        detailViewModel?.updateNote(noteId)
                    } else {
                        detailViewModel?.addNote()
                    }
                }) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }

        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            if (detailUiState.noteAddedStatus) {
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Added Note Successfully")
                    detailViewModel?.resetNoteAddedStatus()
                    onNavigate.invoke()
                }
            }

            if (detailUiState.updateNoteStatus) {
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Note Updated Successfully")
                    detailViewModel?.resetNoteAddedStatus()
                    onNavigate.invoke()

                }
            }

            TextField(
                value = detailUiState.title,
                onValueChange = {
                    detailViewModel?.onTitleChange(it)
                },
                label = { Text(text = "Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = detailUiState.note,
                onValueChange = {
                    detailViewModel?.onNoteChange(it)
                },
                label = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f)

            )
        }


    }
}


@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(detailViewModel = null, noteId = " ") {
    }
}