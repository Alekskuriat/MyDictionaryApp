package com.example.mydictionaryapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.model.entities.DataModel

abstract class BaseViewModel(
    protected val dataLiveData: MutableLiveData<List<DataModel>> = MutableLiveData(),
    protected val errorLiveData: MutableLiveData<Throwable> = MutableLiveData(),
    protected val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
) : ViewModel() {


    open fun getData(word: String, isOnline: Boolean): LiveData<List<DataModel>> = dataLiveData

    open fun getError(): LiveData<Throwable> = errorLiveData

    open fun getLoading(): LiveData<Boolean> = loadingLiveData
}