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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
    }
}
