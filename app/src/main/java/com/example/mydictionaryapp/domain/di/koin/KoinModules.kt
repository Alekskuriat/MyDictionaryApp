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
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class KoinModules {

    fun getModules() = module {
        single<Cicerone<Router>> { Cicerone.create() }
        single<Router> { get<Cicerone<Router>>().router }
        single<NavigatorHolder> { get<Cicerone<Router>>().getNavigatorHolder() }

        single<OkHttpClient> {
            OkHttpClient
                .Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }
        single<HttpLoggingInterceptor> {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        single<CoroutineCallAdapterFactory> { CoroutineCallAdapterFactory() }
        single<ApiService> {
            Retrofit.Builder()
                .baseUrl(KoinModules.BASE_URL)
                .client(get<OkHttpClient>())
                .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        single<DictionaryRemoteDataSource> { DictionaryRemoteDataSourceImpl(apiService = get()) }
        single<DictionaryCacheDataSource> { DictionaryCacheDataSourceImpl() }

        factory<DictionaryRepository> { DictionaryRepositoryImpl(data = get(), cache = get()) }
        factory { DictionaryViewModel(repo = get()) }

    }

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}