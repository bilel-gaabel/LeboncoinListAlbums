package com.billgaag.leboncoin.data.source.remote.api

import com.billgaag.leboncoin.data.source.remote.model.Album
import io.reactivex.Observable
import retrofit2.http.GET

interface AlbumsApi {
    @GET("/img/shared/technical-test.json")
    fun getAll(): Observable<List<Album>>
}