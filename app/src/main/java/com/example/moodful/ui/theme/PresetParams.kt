/*  name: PresetParams.kt
    desc: Contains all preset parameters to clean-up the main class
          SHOULD NOT contain reusable composables. They will be implemented in a
          separate Kotlin file
    date: July 1, 2024
    author: Christian Clyde G. Decierdo
* */

package com.example.moodful.ui.theme

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// CenterAlignedTopAppbar presets. Modify the look of the flavor text here
fun MyTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = ".moodful",
                fontSize = 24.sp, // TODO: Implement a size, padding, and margin class
                style = MaterialTheme.typography.titleMedium
            )
        },
    )
}

@Preview(
    showBackground = true,
)
@Composable
fun ElementPreview() {  // Call a composable function within to render a preview
    MyTopBar()
}
