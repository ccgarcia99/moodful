package com.example.moodful.data
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DiaryRepository(private val diaryEntryDao: DiaryEntryDao) {
    suspend fun insert(diaryEntry: DiaryEntry) {
        withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "Inserting diary entry into database: $diaryEntry")
            diaryEntryDao.insert(diaryEntry)
        }
    }

    fun getAllEntries(): Flow<List<DiaryEntry>> {
        return diaryEntryDao.getAllEntries()
    }

    suspend fun deleteEntry(diaryEntry: DiaryEntry) {
        withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "Deleting diary entry from database: $diaryEntry")
            diaryEntryDao.deleteEntry(diaryEntry)
        }
    }

    suspend fun deleteAllEntries() {
        withContext(Dispatchers.IO) {
            Log.d("DiaryRepository", "Deleting all diary entries from database")
            diaryEntryDao.deleteAllEntries()
        }
    }
}

