package com.example.mydictionaryapp.viewModel.DictionaryViewModel

import androidx.lifecycle.LiveData
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.viewModel.BaseViewModel
import com.example.popularlibraries.domain.schedulers.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DictionaryViewModel
    @Inject constructor(
    private val repo: DictionaryRepository,
    private var schedulers: Schedulers,
    private var disposable: CompositeDisposable,
) : BaseViewModel() {


    override fun getData(word: String, isOnline: Boolean): LiveData<List<DataModel>> {
        loadingData(word)
        return dataLiveData
    }

    private fun loadingData(word: String) {
        disposable.add(
            repo
                .getData(word, true)
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .doOnSubscribe {
                    loadingLiveData.postValue(true)
                }
                .subscribe(
                    {
                        dataLiveData.postValue(it)
                        loadingLiveData.postValue(false)
                    },
                    {
                        errorLiveData.postValue(it)
                        loadingLiveData.postValue(false)
                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}