package com.example.mydictionaryapp.domain.dictionary.dataSource

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.api.ApiService
import io.reactivex.rxjava3.core.Observable

class DictionaryRemoteDataSourceImpl
constructor(
    private val apiService: ApiService
) : DictionaryRemoteDataSource {

    override suspend fun getData(word: String): List<DataModel> =
        apiService.searchAsync(word).await()

}