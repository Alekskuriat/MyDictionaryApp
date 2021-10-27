package com.example.mydictionaryapp.domain.dictionary.repo

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.dictionary.cache.DictionaryCacheDataSource
import com.example.mydictionaryapp.domain.dictionary.dataSource.DictionaryRemoteDataSource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DictionaryRepositoryImpl
@Inject constructor(
    private val data: DictionaryRemoteDataSource,
    private val cache: DictionaryCacheDataSource
) : DictionaryRepository {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<List<DataModel>> {
        return if (fromRemoteSource) {
            data.getData(word)
        } else {
            cache.getData(word)
        }

    }

}