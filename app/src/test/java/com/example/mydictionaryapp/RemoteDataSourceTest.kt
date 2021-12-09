package com.example.mydictionaryapp

import com.example.mydictionaryapp.domain.screens.dictionary.dataSource.DictionaryRemoteDataSource
import kotlinx.coroutines.*
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.test.KoinTest


class RemoteDataSourceTest : KoinTest {

    @Test
    fun remoteDataSource_getData_englishLetters_notNull() {
        assertNotNull(scope.launch {
            repo.getData("Hello")
        })
    }

    @Test
    fun remoteDataSource_getData_russianLetters_notNull() {
        scope.launch {
            assertNotNull(repo.getData("Привет"))
        }
    }

    companion object : KoinComponent {
        val scope = CoroutineScope(Dispatchers.IO)
        val repo: DictionaryRemoteDataSource by inject()
    }
}





