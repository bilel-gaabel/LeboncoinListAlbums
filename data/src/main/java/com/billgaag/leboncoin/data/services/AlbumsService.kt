package com.billgaag.leboncoin.data.services

import com.billgaag.leboncoin.data.source.local.dao.AlbumDao
import com.billgaag.leboncoin.data.source.local.mapper.asDomain
import com.billgaag.leboncoin.data.source.local.mapper.asEntity
import com.billgaag.leboncoin.data.source.remote.api.AlbumsApi
import com.billgaag.leboncoin.data.source.remote.mapper.asDomain
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.exceptions.CompositeException
import java.util.concurrent.TimeUnit

class AlbumsService(
        private val api: AlbumsApi,

        private val dao: AlbumDao,

        private val schedulerForNetork: Scheduler

) : AlbumsRepository {

    override fun getAll(refresh: Boolean): Observable<List<Album>> {
        val getFromLocal =
                RxJavaBridge.toV3Single(dao.getAll()).toObservable().map { it.asDomain() }

        return if (refresh) {

            val getFromRemote = RxJavaBridge.toV3Observable(api.getAll())
                    .map { it.asDomain() }
                    .doOnNext {
                        dao.insertAll(it.asEntity())
                    }

            Observable.concatArrayEagerDelayError(getFromLocal, getFromRemote)
                    .materialize()
                    .filter { !it.isOnError || it.error is CompositeException }
                    .dematerialize { it }
                    .debounce(400, TimeUnit.MILLISECONDS)

        } else {
            getFromLocal.onErrorResumeNext { _: Throwable -> getAll(true) }
        }.subscribeOn(schedulerForNetork)
    }

    override fun getAlbum(id: Int): Observable<Album> {

        return RxJavaBridge.toV3Single(dao.getAlbum(id))
                .toObservable()
                .map { it.asDomain() }
                .onErrorResumeNext { _: Throwable ->
                    RxJavaBridge.toV3Observable(api.getAll())
                            .map { it.asDomain() }
                            .doOnNext {
                                dao.insertAll(it.asEntity())
                            }
                            .map { albums ->
                                val album = albums.firstOrNull() { it.id == id }
                                if (album == null)
                                    throw Exception("Album not fount in remote")
                                album
                            }
                }.subscribeOn(schedulerForNetork)
    }

}