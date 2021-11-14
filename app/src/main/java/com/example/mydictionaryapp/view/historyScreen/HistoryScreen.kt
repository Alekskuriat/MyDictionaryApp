package com.example.mydictionaryapp.view.historyScreen

import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class HistoryScreen {
    fun show() = FragmentScreen { HistoryFragment.newInstance() }
}