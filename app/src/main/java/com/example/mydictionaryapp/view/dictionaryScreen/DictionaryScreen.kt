package com.example.mydictionaryapp.view.dictionaryScreen

import com.github.terrakok.cicerone.androidx.FragmentScreen

class DictionaryScreen {
    fun show() = FragmentScreen { DictionaryFragment.newInstance() }
}