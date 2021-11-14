package com.example.mydictionaryapp.domain.screens.history.cache

import com.example.mydictionaryapp.domain.database.HistoryEntity

interface HistoryCacheDataSource {
    suspend fun getData() : List<HistoryEntity>
}