package com.example.mydictionaryapp.domain.di.modules

import com.example.mydictionaryapp.MainActivity
import com.example.mydictionaryapp.domain.dictionary.cache.DictionaryCacheDataSource
import com.example.mydictionaryapp.domain.dictionary.cache.DictionaryCacheDataSourceImpl
import com.example.mydictionaryapp.domain.dictionary.dataSource.DictionaryRemoteDataSource
import com.example.mydictionaryapp.domain.dictionary.dataSource.DictionaryRemoteDataSourceImpl
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepositoryImpl
import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryFragment
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module
interface DictionaryModule {

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindListCitiesFragment(): DictionaryFragment

    @Binds
    fun bindDictionaryRepository(
        dictionaryRepositoryImpl: DictionaryRepositoryImpl
    ): DictionaryRepository

    @Binds
    fun bindDictionaryRemoteDataSource(
        dictionaryRemoteDataSourceImpl: DictionaryRemoteDataSourceImpl
    ): DictionaryRemoteDataSource

    @Binds
    fun bindDictionaryCacheDataSource(
        dictionaryCacheDataSourceImpl: DictionaryCacheDataSourceImpl
    ): DictionaryCacheDataSource

}