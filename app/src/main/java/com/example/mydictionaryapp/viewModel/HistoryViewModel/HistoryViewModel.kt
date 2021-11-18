package com.example.mydictionaryapp.viewModel.HistoryViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydictionaryapp.domain.database.HistoryEntity
import com.example.mydictionaryapp.domain.screens.history.repo.HistoryRepository
import kotlinx.coroutines.*

class HistoryViewModel(
    private val repo: HistoryRepository
) : ViewModel() {

    private val dataLiveData: MutableLiveData<List<HistoryEntity>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val historySearchLiveData: MutableLiveData<List<HistoryEntity>> = MutableLiveData()
    private var job: Job = Job()
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    internal fun getError(): LiveData<Throwable> = errorLiveData
    internal fun getLoading(): LiveData<Boolean> = loadingLiveData
    internal fun getHistorySearch(word: String) : LiveData<List<HistoryEntity>> {
        searchWord(word)
        return historySearchLiveData
    }

    fun getData(): LiveData<List<HistoryEntity>> {
        loadingData()
        return dataLiveData
    }

    private fun loadingData() {
        job.cancel()
        loadingLiveData.postValue(true)
        job = viewModelCoroutineScope.launch { startRepo() }
    }

    private fun searchWord(word: String) {
        job.cancel()
        job = viewModelCoroutineScope.launch { searchRepo(word) }
    }

    private suspend fun searchRepo(word: String) {
        loadingLiveData.postValue(false)
        historySearchLiveData.postValue(repo.searchWord(word))
    }

    private suspend fun startRepo() {
        loadingLiveData.postValue(false)
        dataLiveData.postValue(repo.getData())
    }

    private fun handleError(error: Throwable) {
        loadingLiveData.postValue(false)
        errorLiveData.postValue(error)
    }

    override fun onCleared() {
        dataLiveData.postValue(null)
        viewModelCoroutineScope.cancel()
        super.onCleared()
    }
}