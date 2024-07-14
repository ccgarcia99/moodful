/*  name: MainActivity.kt
    desc: Class file that contains the NavHost and NavController
    date: July 5, 2024
    author: Christian Clyde G. Decierdo
* */

package com.example.moodful

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodful.controller.ScreenController
import com.example.moodful.data.DiaryDatabase
import com.example.moodful.data.DiaryEntry
import com.example.moodful.data.DiaryRepository
import com.example.moodful.pages.DiaryEntryPage
import com.example.moodful.pages.DiaryView
import com.example.moodful.pages.FrontPage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiaryViewModel(private val repository: DiaryRepository) : ViewModel() {
    private val _selectedColor = MutableStateFlow(Color.Red)
    val selectedColor: StateFlow<Color> = _selectedColor

    private val _entrySaved = MutableLiveData<Boolean>()
    val entrySaved: LiveData<Boolean> get() = _entrySaved

    fun updateColor(color: Color) {
        _selectedColor.value = color
    }

    fun insertDiaryEntry(date: String, time: String, rating: Int, color: Color, text: String) {
        viewModelScope.launch {
            val diaryEntry = DiaryEntry(
                date = date,
                time = time,
                rating = rating,
                color = color.toArgb(),
                text = text
            )
            Log.d("DiaryViewModel", "Inserting diary entry: $diaryEntry")
            repository.insert(diaryEntry)
            _entrySaved.postValue(true)
        }
    }

    fun resetEntrySaved() {
        _entrySaved.value = false
    }

    fun getAllEntries(): Flow<List<DiaryEntry>> {
        return repository.getAllEntries()
    }

    fun deleteDiaryEntry(diaryEntry: DiaryEntry) {
        viewModelScope.launch {
            Log.d("DiaryViewModel", "Deleting diary entry: $diaryEntry")
            repository.deleteEntry(diaryEntry)
        }
    }

    fun deleteAllEntries() {
        viewModelScope.launch {
            Log.d("DiaryViewModel", "Deleting all diary entries")
            repository.deleteAllEntries()
        }
    }
}

class DiaryViewModelFactory(private val repository: DiaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainActivity : ComponentActivity() {
    private lateinit var diaryDatabase: DiaryDatabase
    private lateinit var diaryRepository: DiaryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        diaryDatabase = DiaryDatabase.getDatabase(applicationContext)
        diaryRepository = DiaryRepository(diaryDatabase.diaryEntryDao())

        setContent {
            val diaryViewModel: DiaryViewModel = viewModel(
                factory = DiaryViewModelFactory(diaryRepository)
            )
            Navigation(diaryViewModel)
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun Navigation(diaryViewModel: DiaryViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenController.FrontPage.route) {
        composable(route = ScreenController.FrontPage.route) {
            FrontPage(navController = navController)
        }
        composable(route = ScreenController.DiaryEntry.route) {
            DiaryEntryPage(
                diaryViewModel = diaryViewModel,
                navController = navController
            )
        }
        composable(route = ScreenController.DiaryView.route) {
            DiaryView(
                navController = navController,
                diaryViewModel = diaryViewModel
            )
        }
    }
}

