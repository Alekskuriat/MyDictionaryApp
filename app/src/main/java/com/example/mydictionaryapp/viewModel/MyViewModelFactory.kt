package com.example.mydictionaryapp.viewModel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import com.example.popularlibraries.domain.schedulers.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable


class MyViewModelFactory(
    private val repo: DictionaryRepository,
    private var schedulers: Schedulers,
    private var disposable: CompositeDisposable
) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DictionaryViewModel(repo, schedulers, disposable) as T
    }

}