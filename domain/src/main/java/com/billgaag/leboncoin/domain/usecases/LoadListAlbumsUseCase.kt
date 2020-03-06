package com.billgaag.leboncoin.domain.usecases

import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class LoadListAlbumsUseCase
@Inject
constructor(
        private val albumsRepository: AlbumsRepository
) : BaseUseCase.Inputless<Observable<List<Album>>> {

    override fun execute(): Observable<List<Album>> {
        return albumsRepository.getAll(true)
    }
}