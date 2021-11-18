package com.example.mydictionaryapp.domain.screens.history.cache

import com.example.mydictionaryapp.domain.database.HistoryDao
import com.example.mydictionaryapp.domain.database.HistoryEntity

class HistoryCacheDataSourceImpl(
    private val dataBase: HistoryDao
) : HistoryCacheDataSource {

    override suspend fun getData(): List<HistoryEntity> = dataBase.all()

}