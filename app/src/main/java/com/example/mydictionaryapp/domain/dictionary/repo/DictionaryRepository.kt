package com.example.mydictionaryapp.domain.dictionary.repo

import com.example.dictionaryapp.model.entities.DataModel
import io.reactivex.rxjava3.core.Observable

interface DictionaryRepository {

    suspend fun getData(word: String, fromRemoteSource: Boolean): List<DataModel>

}