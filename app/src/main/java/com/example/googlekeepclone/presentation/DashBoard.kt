package com.example.googlekeepclone.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
    Column(modifier = Modifier.padding(8.dp)) {
        SearchAppBar()
    }
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
                        tint = Color.White
                    )
                }
            }
        )
    }
    ) {
        Column {
            NotesList()
            Spacer(modifier = Modifier.height(628.dp))
            BottomBar()

        }
    }

}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun NotesList() {
    val Text = String
    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 100.dp)) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            content = {
                Text
            })

    }

}

@Composable
fun BottomBar() {
    Row(horizontalArrangement = Arrangement.Start) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Profile",
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(20.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_brush),
            contentDescription = "search",
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(20.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_mic),
            contentDescription = "speaker",
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(212.dp))
        FloatingActionButton(
            onClick = {},
            modifier = Modifier.size(40.dp)
        ) {
            Icon(Icons.Filled.Add, "")
        }


    }
}


@Preview
@Composable
fun PreviewThis() {
    Dashboard()

}