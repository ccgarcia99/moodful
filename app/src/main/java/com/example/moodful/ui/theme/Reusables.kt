/*  name: Reusables.kt
    desc: Contains all preset parameters to clean-up the main class
          SHOULD NOT contain reusable composables. They will be implemented in a
          separate Kotlin file
    date: July 1, 2024
    author: Christian Clyde G. Decierdo
* */

package com.example.moodful.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// TODO: Rename to Reusables.kt. Refactor functions to reflect Kotlin file purpose
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableTopAppBar(
    text: String,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = text,
                fontSize = 24.sp, // TODO: Implement a size, padding, and margin class
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.outline
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

