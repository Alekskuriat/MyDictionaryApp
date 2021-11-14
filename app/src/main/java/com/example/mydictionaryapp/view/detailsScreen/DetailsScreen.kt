package com.example.mydictionaryapp.view.detailsScreen

import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class DetailsScreen {
    fun show(data: DataModel) = FragmentScreen { DetailsFragment.newInstance(data) }
}