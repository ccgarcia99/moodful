/*  name: MainActivity.kt
    desc: Class file that contains the NavHost and NavController
    date: July 5, 2024
    author: Christian Clyde G. Decierdo
* */

package com.example.moodful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodful.controller.ScreenController
import com.example.moodful.pages.DiaryEntryPage
import com.example.moodful.pages.DiaryView
import com.example.moodful.pages.FrontPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}

// Navigation controller composable function
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenController.FrontPage.route) {
        composable(route = ScreenController.FrontPage.route) {
            FrontPage(navController = navController)
        }
        composable(route = ScreenController.DiaryEntry.route) {
            DiaryEntryPage()
        }
        composable(route = ScreenController.DiaryView.route) {
            DiaryView()
        }
    }
}