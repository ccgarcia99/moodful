package com.example.moodful.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diaryEntry: DiaryEntry)

    @Query("SELECT * FROM diary_entries ORDER BY date DESC, time DESC")
    fun getAllEntries(): Flow<List<DiaryEntry>>

    @Delete
    suspend fun deleteEntry(diaryEntry: DiaryEntry)

    @Query("DELETE FROM diary_entries")
    suspend fun deleteAllEntries()
}
