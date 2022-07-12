package com.example.googlekeepclone.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.googlekeepclone.R

@Composable
fun Dashboard() {

}

@Composable
fun SearchAppBar() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Search your notes"
            )
        },
            actions = {
                IconButton(onClick = {})
                {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            }
        )
    }
    ) {}

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesList(Text: String) {
    Column() {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            content = {
                Text
            })
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    }

}

@Preview
@Composable
fun PreviewThis() {

}