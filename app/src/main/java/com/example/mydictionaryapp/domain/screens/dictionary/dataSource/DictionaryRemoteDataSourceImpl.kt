package com.example.mydictionaryapp.domain.screens.dictionary.dataSource

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.api.ApiService

class DictionaryRemoteDataSourceImpl
    (
    private val apiService: ApiService
) : DictionaryRemoteDataSource {

    override suspend fun getData(word: String): List<DataModel> =
        apiService.searchAsync(word).await()

}