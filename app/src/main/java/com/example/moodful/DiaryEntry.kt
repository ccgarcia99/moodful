/*  name: DiaryEntry.kt
    desc: Activity that contains elements such as mood picker, data and time tracking,
          and diary entries. All args will be passed to some Kotlin file that will store
          every data locally.
    date: July 5, 2024
    author: Christian Clyde G. Decierdo
* */

package com.example.moodful
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moodful.ui.theme.MoodfulTheme
import com.example.moodful.ui.theme.MyTopBar

@Composable
fun DiaryEntryPage(modifier: Modifier = Modifier){
    MoodfulTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Scaffold(
            topBar = { MyTopBar() }
        ) {
            innerPadding -> Surface(    // Spam across the screen
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DiaryEntryPreview(modifier: Modifier = Modifier) {
    DiaryEntryPage()
}