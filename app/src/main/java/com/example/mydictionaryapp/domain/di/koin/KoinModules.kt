package com.example.mydictionaryapp.domain.di.koin

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryFragment
import com.example.mydictionaryapp.view.historyScreen.HistoryFragment
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import com.example.mydictionaryapp.viewModel.HistoryViewModel.HistoryViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KoinModules {

    fun getModules() = module {
        scope(named("Dictionary_fragment")) {
            scoped<DictionaryRemoteDataSource> { DictionaryRemoteDataSourceImpl(apiService = get()) }
            scoped<DictionaryCacheDataSource> { DictionaryCacheDataSourceImpl(database = get()) }
            factory<DictionaryRepository> { DictionaryRepositoryImpl(data = get(), cache = get()) }
            viewModel { DictionaryViewModel(repo = get()) }
        }

        scope(named("History_fragment")) {
            scoped<HistoryCacheDataSource> { HistoryCacheDataSourceImpl(dataBase = get()) }
            factory<HistoryRepository> { HistoryRepositoryImpl(cache = get()) }
            viewModel { HistoryViewModel(repo = get()) }
        }

        single<Cicerone<Router>> { Cicerone.create() }
        single<Router> { get<Cicerone<Router>>().router }
        single<NavigatorHolder> { get<Cicerone<Router>>().getNavigatorHolder() }

        single {
            Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB")
                .addMigrations(object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE HistoryEntity ADD COLUMN imageUrl text DEFAULT ''")
                    }
                })
                .build()
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


    }

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}