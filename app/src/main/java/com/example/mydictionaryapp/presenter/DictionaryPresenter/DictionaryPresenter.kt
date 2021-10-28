package com.example.mydictionaryapp.presenter.DictionaryPresenter

import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryView

interface DictionaryPresenter<V: DictionaryView> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)

}