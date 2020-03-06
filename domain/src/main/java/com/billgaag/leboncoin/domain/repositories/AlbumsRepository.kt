package com.billgaag.leboncoin.domain.repositories

import com.billgaag.leboncoin.domain.model.Album
import io.reactivex.rxjava3.core.Observable

interface AlbumsRepository {

    fun getAll(refresh: Boolean = true): Observable<List<Album>>

    fun getAlbum(id: Int): Observable<Album>
}