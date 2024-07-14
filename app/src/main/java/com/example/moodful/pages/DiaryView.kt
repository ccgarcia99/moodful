package com.example.moodful.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodful.DiaryViewModel
import com.example.moodful.data.DiaryEntry
import com.example.moodful.ui.theme.MoodfulTheme
import com.example.moodful.ui.theme.ReusableTopAppBar
import kotlinx.coroutines.launch

@Composable
fun DiaryView(
    diaryViewModel: DiaryViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val entriesState = diaryViewModel.getAllEntries().collectAsState(initial = emptyList())

    MoodfulTheme {
        Scaffold(
            topBar = { ReusableTopAppBar(text = "My Diary", navController = navController) }
        ) { innerPadding ->
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
                        DiaryEntryItem(
                            entry = entry,
                            onDeleteClick = {
                                coroutineScope.launch {
                                    diaryViewModel.deleteDiaryEntry(entry)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DiaryEntryItem(entry: DiaryEntry, onDeleteClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(entry.color),
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date: ${entry.date}",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }
            Text(text = "Time: ${entry.time}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Feel: ${entry.rating}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = entry.text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

