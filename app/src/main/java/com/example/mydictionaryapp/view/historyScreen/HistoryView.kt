package com.example.mydictionaryapp.view.historyScreen

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.database.HistoryEntity

interface HistoryView {
    fun showWords(data: List<HistoryEntity>?)
    fun showError(error: Throwable)
    fun showLoading()
}