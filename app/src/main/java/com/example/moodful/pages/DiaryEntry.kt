package com.example.moodful.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moodful.ui.theme.MoodfulTheme
import com.example.moodful.ui.theme.MyTopBar

@Composable
fun DiaryEntryPage(modifier: Modifier = Modifier) {
    MoodfulTheme {
        Scaffold(
            topBar = { MyTopBar() }
        ) { innerPadding ->
            Surface(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.surface
            ) {
                // Content of DiaryEntryPage
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiaryEntryPreview(modifier: Modifier = Modifier) {
    DiaryEntryPage()
}
