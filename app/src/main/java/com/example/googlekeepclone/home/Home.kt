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
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit){
        homeViewModel?.loadNotes()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navToDetailPage.invoke()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_home),
                    contentDescription = null
                )

            }
        },
        topBar = {
            TopAppBar(
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
                title = { Text(text = "Home")}
            )
        }
    ) {   padding->
        Column(modifier = Modifier.padding(padding)) {
            when(homeUiState.noteslist){
                is Resources.Loading ->{
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }
                is Resources.Success->{
                    LazyVerticalGrid(cells = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                    ){
                        items(homeUiState.noteslist.data?: emptyList()){note ->
                            NoteItem(
                                notes = note ,
                                onLongClick = {
                                    openDialog= true
                                    selectedNote = note
                                },
                            ) {
                                onNoteClick.invoke(note.documentId)
                            }
                        }

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
                                        backgroundColor = Color.Magenta
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
                    Text(text = homeUiState
                        .noteslist.throwable?.localizedMessage?:"Unknown Error",
                    color = Color.Magenta
                    )
                }
            }
            
        }

    }
    LaunchedEffect(key1 = homeViewModel?.hasUser){
        if(homeViewModel?.hasUser == false){
            navToLoginPage.invoke()
        }
    }
}
@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    notes: Notes,
    onLongClick:() ->Unit,
    onClick:()-> Unit
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
//            CompositionLocalProvider(
//                LocalContentAlpha provides ContentAlpha.disabled
//            ) {
//
//                Text(text = formatDate(notes.timestamp),
//                    style = MaterialTheme.typography.body1,
//                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier
//                    .padding(4.dp)
//                    .align(Alignment.End),
//                maxLines = 4
//                )
//            }
//            Spacer(modifier = Modifier.size(4.dp))
//
//        }

        }

    }
}

//}