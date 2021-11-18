package com.example.mydictionaryapp.domain.screens.history.repo

import com.example.mydictionaryapp.domain.database.HistoryEntity

interface HistoryRepository {
    suspend fun getData() : List<HistoryEntity>
    suspend fun searchWord(word: String) : List<HistoryEntity>
}