package com.example.moodful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.moodful.ui.theme.MoodfulTheme
import com.example.moodful.ui.theme.MyTopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RootComposable()
        }
    }
}


@Composable
fun RootComposable(modifier: Modifier = Modifier) {
    MoodfulTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        Scaffold (
            // TODO: Implement sidebar menu
            topBar = { MyTopBar() }
        ){
            innerPadding -> Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.surface
            ) {
                ConstraintLayout {
                    // TODO: Create refs here
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppPreview() {
    RootComposable()
}