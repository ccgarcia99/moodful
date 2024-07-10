package com.example.moodful.controller

sealed class ScreenController(
    val route: String
){
    data object FrontPage : ScreenController(route = "FrontPage")
    data object DiaryEntry : ScreenController(route = "DiaryEntryPage")
    data object DiaryView : ScreenController(route = "DiaryView")
}

sealed class DiaryEntryRefs(val refs: String) {
    data object TimeStamp : DiaryEntryRefs(refs = "TimeStamp")
    data object ColorPicker : DiaryEntryRefs(refs = "ColorPicker")
    data object TextField : DiaryEntryRefs(refs = "Diary Entry")
}