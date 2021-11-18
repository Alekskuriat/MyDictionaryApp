package com.example.mydictionaryapp.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(HistoryEntity::class),
    version = 2,
    exportSchema = true
)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}