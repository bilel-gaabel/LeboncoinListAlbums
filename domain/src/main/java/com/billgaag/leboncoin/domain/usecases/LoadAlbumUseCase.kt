package com.billgaag.leboncoin.domain.usecases

import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LoadAlbumUseCase
@Inject
constructor(
        private val albumsRepository: AlbumsRepository
) : BaseUseCase<Int, Observable<Album>> {

    override fun execute(input: Int): Observable<Album> {
        return albumsRepository.getAlbum(input)
    }
}