package com.example.mydictionaryapp.domain.screens.dictionary.dataSource

import com.example.dictionaryapp.model.entities.DataModel

interface DictionaryRemoteDataSource {

    suspend fun getData(word: String): List<DataModel>

}