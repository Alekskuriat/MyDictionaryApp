package com.example.mydictionaryapp.domain.screens.dictionary.cache

import com.example.dictionaryapp.model.entities.DataModel
import com.example.dictionaryapp.model.entities.Meanings
import com.example.dictionaryapp.model.entities.Translation
import com.example.mydictionaryapp.domain.database.HistoryDao
import com.example.mydictionaryapp.domain.database.HistoryEntity

class DictionaryCacheDataSourceImpl(
    private val database: HistoryDao
) : DictionaryCacheDataSource {

    override suspend fun saveToDB(data: DataModel) {
        database.insert(
            HistoryEntity(
                word = data.text.toString(),
                description = data.meanings?.first()?.translation?.translation.toString(),
                imageUrl = data.meanings?.first()?.imageUrl
            )
        )
    }

    override suspend fun getData(word: String): DataModel {
        val wordInDB = database.getDataByWord(word)
        return DataModel(
            text = wordInDB.word,
            listOf(
                Meanings(
                    Translation(
                        translation = wordInDB.description,
                        note = null
                    ),
                    imageUrl = wordInDB.imageUrl,
                    difficultyLevel = 0
                )
            )
        )
    }

}