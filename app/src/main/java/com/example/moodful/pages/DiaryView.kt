package com.example.moodful.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodful.DiaryViewModel
import com.example.moodful.data.DiaryEntry
import com.example.moodful.ui.theme.MoodfulTheme

@Composable
fun DiaryView(
    diaryViewModel: DiaryViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val entriesState = diaryViewModel.getAllEntries().collectAsState(initial = emptyList())

    MoodfulTheme {
        Scaffold { innerPadding ->
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.surface
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(entriesState.value) { entry ->
                        DiaryEntryItem(entry)
                    }
                }
            }
        }
    }
}

@Composable
fun DiaryEntryItem(entry: DiaryEntry) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(entry.color),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${entry.date}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Time: ${entry.time}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Mood Rating: ${entry.rating}", style = MaterialTheme.typography.titleSmall)

            Text(text = entry.text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

