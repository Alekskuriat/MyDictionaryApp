package com.example.mydictionaryapp.viewModel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import com.example.popularlibraries.domain.schedulers.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.lang.IllegalArgumentException


@Singleton
class MyViewModelFactory
@Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = viewModels[modelClass]
            ?: viewModels.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}