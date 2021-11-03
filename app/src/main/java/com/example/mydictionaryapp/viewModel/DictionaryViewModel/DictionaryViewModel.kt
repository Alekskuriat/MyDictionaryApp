package com.example.mydictionaryapp.viewModel.DictionaryViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.api.ApiService
import com.example.mydictionaryapp.domain.dictionary.cache.DictionaryCacheDataSourceImpl
import com.example.mydictionaryapp.domain.dictionary.dataSource.DictionaryRemoteDataSourceImpl
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepositoryImpl
import com.example.popularlibraries.domain.schedulers.DefaultSchedulers
import com.example.popularlibraries.domain.schedulers.Schedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DictionaryViewModel(
    private val repo: DictionaryRepository,
    private var schedulers: Schedulers,
    private var disposable: CompositeDisposable,
    private val dataLiveData: MutableLiveData<List<DataModel>> = MutableLiveData(),
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData(),
    private val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData(),
) : ViewModel() {


    fun getData(word: String, isOnline: Boolean): LiveData<List<DataModel>> {
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

    fun getError(): LiveData<Throwable> = errorLiveData

    fun getLoading(): LiveData<Boolean> = loadingLiveData

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}