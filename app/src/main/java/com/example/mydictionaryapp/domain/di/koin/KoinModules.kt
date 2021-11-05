package com.example.mydictionaryapp.domain.di.koin

import com.example.mydictionaryapp.MainActivity
import com.example.mydictionaryapp.domain.api.ApiService
import com.example.mydictionaryapp.domain.dictionary.cache.DictionaryCacheDataSource
import com.example.mydictionaryapp.domain.dictionary.cache.DictionaryCacheDataSourceImpl
import com.example.mydictionaryapp.domain.dictionary.dataSource.DictionaryRemoteDataSource
import com.example.mydictionaryapp.domain.dictionary.dataSource.DictionaryRemoteDataSourceImpl
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepositoryImpl
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import com.example.popularlibraries.domain.schedulers.DefaultSchedulers
import com.example.popularlibraries.domain.schedulers.Schedulers
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class KoinModules {

    fun getModules() = module {

        single<Schedulers> {
            DefaultSchedulers()
        }
        single<CompositeDisposable> {
            CompositeDisposable()
        }
        single<ApiService> {
            Retrofit.Builder()
                .baseUrl(KoinModules.BASE_URL)
                .client(
                    OkHttpClient
                        .Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                )
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        single<DictionaryRemoteDataSource> { DictionaryRemoteDataSourceImpl(apiService = get()) }
        single<DictionaryCacheDataSource> { DictionaryCacheDataSourceImpl() }

        factory<DictionaryRepository> { DictionaryRepositoryImpl(data = get(), cache = get()) }
        factory { DictionaryViewModel(repo = get(), schedulers = get(), disposable = get()) }

    }

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}