package com.example.mydictionaryapp.domain.screens.dictionary.repo

import com.example.dictionaryapp.model.entities.DataModel

interface DictionaryRepository {

    suspend fun getData(word: String, fromRemoteSource: Boolean): List<DataModel>

}