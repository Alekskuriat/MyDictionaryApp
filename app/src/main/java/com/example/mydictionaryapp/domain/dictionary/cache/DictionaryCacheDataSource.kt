package com.example.mydictionaryapp.domain.dictionary.cache

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.database.HistoryEntity
import com.example.mydictionaryapp.domain.dictionary.dataSource.DictionaryRemoteDataSource

interface DictionaryCacheDataSource {
    suspend fun saveToDB(data: DataModel)
    suspend fun getData(word: String): DataModel
}