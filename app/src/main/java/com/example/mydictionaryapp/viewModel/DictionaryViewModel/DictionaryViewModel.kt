package com.example.mydictionaryapp.viewModel.DictionaryViewModel

import androidx.lifecycle.LiveData
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.viewModel.BaseViewModel
import kotlinx.coroutines.*

class DictionaryViewModel(
    private val repo: DictionaryRepository
) : BaseViewModel() {

    private var job: Job? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<List<DataModel>> {
        loadingData(word)
        return dataLiveData
    }

    private fun loadingData(word: String) {
        cancelJob(job)
        loadingLiveData.postValue(true)
        job = viewModelCoroutineScope.launch { startRepo(word) }
    }

    private suspend fun startRepo(word: String) {
        loadingLiveData.postValue(false)
        dataLiveData.postValue(repo.getData(word, true))
    }

    override fun handleError(error: Throwable) {
        loadingLiveData.postValue(false)
        errorLiveData.postValue(error)
    }

    override fun onCleared() {
        dataLiveData.postValue(null)
        viewModelCoroutineScope.cancel()
        super.onCleared()
    }
}