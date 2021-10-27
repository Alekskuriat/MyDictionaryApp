package com.example.mydictionaryapp.domain.dictionary.dataSource

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.api.ApiService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DictionaryRemoteDataSourceImpl
@Inject constructor(
    private val apiService: ApiService
) : DictionaryRemoteDataSource {

    override fun getData(word: String): Observable<List<DataModel>> =
        apiService.search(word)

}