package com.example.mydictionaryapp.view.dictionaryScreen

import com.example.dictionaryapp.model.entities.DataModel

interface DictionaryView {

    fun showWords(data: List<DataModel>?)
    fun showError(error: Throwable)
    fun showLoading()

}