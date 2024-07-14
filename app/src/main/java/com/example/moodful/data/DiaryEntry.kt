package com.example.moodful.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "diary_entries")
data class DiaryEntry(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val date: String,
    val time: String,
    val rating: Int,
    val color: Int,
    val text: String
)