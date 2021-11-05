package com.example.mydictionaryapp.domain.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydictionaryapp.domain.di.viewModel.ViewModelKey
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import com.example.mydictionaryapp.viewModel.MyViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [DictionaryModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DictionaryViewModel::class)
    protected abstract fun mainViewModel(viewModel: DictionaryViewModel): ViewModel

}