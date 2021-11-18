package com.example.mydictionaryapp.domain.screens.history.repo

import com.example.mydictionaryapp.domain.database.HistoryEntity
import com.example.mydictionaryapp.domain.screens.history.cache.HistoryCacheDataSource

class HistoryRepositoryImpl(
    private val cache: HistoryCacheDataSource
) : HistoryRepository {

    override suspend fun getData(): List<HistoryEntity> = cache.getData()

    override suspend fun searchWord(word: String): List<HistoryEntity> {
        val listSearchWord = mutableListOf<HistoryEntity>()
        for (entity in cache.getData()) {
            if (entity.word == word || entity.description == word)
                listSearchWord.add(entity)
        }
        return listSearchWord
    }


}







