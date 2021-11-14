package com.example.mydictionaryapp.domain.screens.dictionary.repo

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.screens.dictionary.cache.DictionaryCacheDataSource
import com.example.mydictionaryapp.domain.screens.dictionary.dataSource.DictionaryRemoteDataSource

class DictionaryRepositoryImpl
    (
    private val data: DictionaryRemoteDataSource,
    private val cache: DictionaryCacheDataSource
) : DictionaryRepository {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): List<DataModel> {
        val listData = data.getData(word)
        if (!listData.isNullOrEmpty()) {
            for (entity in listData) {
                cache.saveToDB(entity)
            }
        }
        return listData
    }

}