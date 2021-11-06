package com.example.mydictionaryapp.domain.dictionary.cache

import com.example.dictionaryapp.model.entities.DataModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DictionaryCacheDataSourceImpl : DictionaryCacheDataSource {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }

}