package com.example.googlekeepclone.home

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.googlekeepclone.R
import com.example.googlekeepclone.Utils
import com.example.googlekeepclone.model.Notes
import com.example.googlekeepclone.repository.Resources
import java.util.*
import androidx.compose.foundation.combinedClickable as combinedClickable1

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    onNoteClick: (id: String) -> Unit,
    navToDetailPage: () -> Unit,
    navToLoginPage: () -> Unit,
) {
    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedNote: Notes? by remember {
        mutableStateOf(null)
    }
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        homeViewModel?.loadNotes()
    }


    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
                    BottomAppBar(backgroundColor = Color.LightGray,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(painter = painterResource(id =R.drawable.ic_brush ),
                            contentDescription = null,
                            tint = Color.Black)
                        Spacer(modifier = Modifier.width(24.dp))
                        Icon(painter = painterResource(id =R.drawable.ic_mic ),
                            contentDescription = null,
                            tint = Color.Black)
                        Spacer(modifier = Modifier.width(24.dp))
                        Icon(painter = painterResource(id =R.drawable.ic_image ),
                            contentDescription = null,
                            tint = Color.Black)
                        
                    }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(backgroundColor = Color.LightGray,onClick = {
                navToDetailPage.invoke()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_home),
                    contentDescription = null,
                )

            }
        },
        topBar = {
            TopAppBar(backgroundColor = Color.LightGray,
                navigationIcon = {},
                actions = {
                    IconButton(onClick = {
                        homeViewModel?.signOut()
                        navToLoginPage.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                        )
                    }
                },
                title = { Text(text = "Notes") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (homeUiState.noteslist) {
                is Resources.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
                is Resources.Success -> {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        items(homeUiState.noteslist.data?:emptyList()) { note ->
                            NoteItem(
                                notes = note,
                                onLongClick = {
                                    openDialog = true
                                    selectedNote = note
                                },
                            ) {
                                onNoteClick.invoke(note.documentId)
                            }
                        }

                    }
                    Row(verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start) {
                        Icon(painter = painterResource(id =R.drawable.ic_brush ),
                            contentDescription = null,
                        tint = Color.Black)
                        Icon(painter = painterResource(id =R.drawable.ic_mic ),
                            contentDescription = null,
                            tint = Color.Black)
                        Icon(painter = painterResource(id =R.drawable.ic_image ),
                            contentDescription = null,
                            tint = Color.Black)

                    }
                    AnimatedVisibility(visible = openDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog = false
                            },
                            title = { Text(text = "Delete Note?") },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        selectedNote?.documentId?.let {
                                            homeViewModel?.deleteNote(it)
                                        }
                                        openDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Gray
                                    ),
                                ) {
                                    Text(text = "Delete ")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { openDialog = false }) {
                                    Text(text = "Cancel")
                                }
                            }
                        )
                    }
                }
                else -> {
                    Text(
                        text = homeUiState
                            .noteslist.throwable?.localizedMessage ?: "Unknown Error",
                        color = Color.Magenta
                    )
                }
            }
        }
    }
    LaunchedEffect(key1 = homeViewModel?.hasUser) {
        if (homeViewModel?.hasUser == false) {
            navToLoginPage.invoke()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    notes: Notes,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable1(
                onLongClick = { onLongClick.invoke() },
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = Utils.colors[notes.colorIndex]
    ) {
        Column {
            Text(
                text = notes.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = notes.description,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Normal,
                maxLines = 8,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun PrevHomeScreen() {

        Home(
            homeViewModel = null,
            onNoteClick = {},
            navToDetailPage = { /*TODO*/ }
        ) {


    }
}
