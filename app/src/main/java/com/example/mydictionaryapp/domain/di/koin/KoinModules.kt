package com.example.mydictionaryapp.domain.di.koin

import androidx.room.Room
import com.example.mydictionaryapp.BuildConfig
import com.example.mydictionaryapp.domain.api.ApiService
import com.example.mydictionaryapp.domain.database.HistoryDao
import com.example.mydictionaryapp.domain.database.HistoryDataBase
import com.example.mydictionaryapp.domain.screens.dictionary.cache.DictionaryCacheDataSource
import com.example.mydictionaryapp.domain.screens.dictionary.cache.DictionaryCacheDataSourceImpl
import com.example.mydictionaryapp.domain.screens.dictionary.dataSource.DictionaryRemoteDataSource
import com.example.mydictionaryapp.domain.screens.dictionary.dataSource.DictionaryRemoteDataSourceImpl
import com.example.mydictionaryapp.domain.screens.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.domain.screens.dictionary.repo.DictionaryRepositoryImpl
import com.example.mydictionaryapp.domain.screens.history.cache.HistoryCacheDataSource
import com.example.mydictionaryapp.domain.screens.history.cache.HistoryCacheDataSourceImpl
import com.example.mydictionaryapp.domain.screens.history.repo.HistoryRepository
import com.example.mydictionaryapp.domain.screens.history.repo.HistoryRepositoryImpl
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import com.example.mydictionaryapp.viewModel.HistoryViewModel.HistoryViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KoinModules {

    fun getModules() = module {
        single<Cicerone<Router>> { Cicerone.create() }
        single<Router> { get<Cicerone<Router>>().router }
        single<NavigatorHolder> { get<Cicerone<Router>>().getNavigatorHolder() }

        single {
            Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build()
        }
        single<HistoryDao> { get<HistoryDataBase>().historyDao() }




        single<OkHttpClient> {
            val okHttpClient = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) okHttpClient.addInterceptor(get<HttpLoggingInterceptor>())
            return@single okHttpClient.build()
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
        single<DictionaryCacheDataSource> { DictionaryCacheDataSourceImpl(database = get()) }
        single<HistoryCacheDataSource> {HistoryCacheDataSourceImpl(dataBase = get())}

        factory<DictionaryRepository> { DictionaryRepositoryImpl(data = get(), cache = get()) }
        factory<HistoryRepository> { HistoryRepositoryImpl(cache = get()) }
        factory { DictionaryViewModel(repo = get()) }
        factory { HistoryViewModel(repo = get()) }

    }

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}