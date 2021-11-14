package com.example.mydictionaryapp.domain.screens.history.repo

import com.example.mydictionaryapp.domain.database.HistoryEntity
import com.example.mydictionaryapp.domain.screens.history.cache.HistoryCacheDataSource

class HistoryRepositoryImpl(
    private val cache: HistoryCacheDataSource
) : HistoryRepository {

    override suspend fun getData(): List<HistoryEntity> = cache.getData()

}