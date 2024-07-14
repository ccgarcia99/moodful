package com.example.moodful

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import com.example.moodful.pages.DiaryEntryPage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.moodful.pages.DiaryView
import com.example.moodful.pages.FrontPage
import com.example.moodful.ui.theme.MoodfulTheme

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Front Page - ExFAB disabled"
)
@Composable
fun FPPreviewPortrait1() {
    val mockNavController = rememberNavController()
    FrontPage(
        navController = mockNavController,
        previewExpanded = false
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Front Page - ExFAB enabled"
)
@Composable
fun FPPreviewPortrait2() {
    val mockNavController = rememberNavController()
    FrontPage(
        navController = mockNavController,
        previewExpanded = true
    )
}

@Preview(
    showBackground = true,
    widthDp = 640,
    heightDp = 360,
    name = "Front Page - ExFAB disabled"
)
@Composable
fun FPPreviewLandscape1() {
    val mockNavController = rememberNavController()
    FrontPage(
        navController = mockNavController,
        previewExpanded = false
    )
}

@Preview(
    showBackground = true,
    widthDp = 640,
    heightDp = 360,
    name = "Front Page - ExFAB enabled"
)
@Composable
fun FPPreviewLandscape2() {
    val mockNavController = rememberNavController()
    FrontPage(
        navController = mockNavController,
        previewExpanded = true
    )
}
