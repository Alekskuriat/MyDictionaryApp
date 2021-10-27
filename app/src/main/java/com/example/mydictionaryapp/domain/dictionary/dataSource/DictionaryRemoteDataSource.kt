package com.example.mydictionaryapp.domain.dictionary.dataSource

import com.example.dictionaryapp.model.entities.DataModel
import io.reactivex.rxjava3.core.Observable

interface DictionaryRemoteDataSource {

    fun getData(word: String): Observable<List<DataModel>>

}