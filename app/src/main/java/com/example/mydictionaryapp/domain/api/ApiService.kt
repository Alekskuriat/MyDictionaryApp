package com.example.mydictionaryapp.domain.api


import com.example.dictionaryapp.model.entities.DataModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(GET_INQUIRY)
    fun searchAsync(
        @Query(QUERY_INQUIRY) wordToSearch: String
    ): Deferred<List<DataModel>>

    companion object {
        private const val GET_INQUIRY = "words/search"
        private const val QUERY_INQUIRY = "search"
    }

}