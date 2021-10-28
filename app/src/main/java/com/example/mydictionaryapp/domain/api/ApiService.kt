package com.example.mydictionaryapp.domain.api


import com.example.dictionaryapp.model.entities.DataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(GET_INQUIRY)
    fun search(
        @Query(QUERY_INQUIRY) wordToSearch: String
    ): Observable<List<DataModel>>

    companion object {
        private const val GET_INQUIRY = "words/search"
        private const val QUERY_INQUIRY = "search"
    }

}