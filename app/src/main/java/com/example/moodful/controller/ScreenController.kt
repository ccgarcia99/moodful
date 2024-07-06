package com.example.moodful.controller

sealed class ScreenController(
    val route: String
){
    data object FrontPage : ScreenController(route = "FrontPage")
    data object DiaryEntry : ScreenController(route = "DiaryEntryPage")
    data object DiaryView : ScreenController(route = "DiaryView")
}