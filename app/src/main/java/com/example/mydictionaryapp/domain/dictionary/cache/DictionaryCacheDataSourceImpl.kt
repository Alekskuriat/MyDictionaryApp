package com.example.mydictionaryapp.domain.dictionary.cache

import com.example.dictionaryapp.model.entities.DataModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DictionaryCacheDataSourceImpl
    @Inject constructor(

    ): DictionaryCacheDataSource {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }

}