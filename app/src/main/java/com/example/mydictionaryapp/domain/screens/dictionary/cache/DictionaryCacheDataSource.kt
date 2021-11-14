package com.example.mydictionaryapp.domain.screens.dictionary.cache

import com.example.dictionaryapp.model.entities.DataModel

interface DictionaryCacheDataSource {
    suspend fun saveToDB(data: DataModel)
    suspend fun getData(word: String): DataModel
}