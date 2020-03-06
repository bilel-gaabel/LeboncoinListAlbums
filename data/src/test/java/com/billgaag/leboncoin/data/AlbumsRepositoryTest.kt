package com.billgaag.leboncoin.data

import com.billgaag.leboncoin.data.services.AlbumsService
import com.billgaag.leboncoin.data.source.local.dao.AlbumDao
import com.billgaag.leboncoin.data.source.local.mapper.asEntity
import com.billgaag.leboncoin.data.source.remote.api.AlbumsApi
import com.billgaag.leboncoin.data.source.remote.mapper.asRemote
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxjava3.exceptions.CompositeException
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumsRepositoryTest {

    @Mock
    private lateinit var api: AlbumsApi

    @Mock
    private lateinit var dao: AlbumDao

    private lateinit var repository: AlbumsRepository

    @Before
    fun setUp() {
        repository = AlbumsService(api, dao, Schedulers.trampoline())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Get all album with success when Albums exist in local and refresh from remote`() {
        // GIVEN -> Preparation

        val domainAlbumsOut =
                listOf(MockedObjects.domainFirstAlbum, MockedObjects.domainSecondAlbum)
        val remoteAlbumsOut = domainAlbumsOut.asRemote()
        val entityAlbumsOut = domainAlbumsOut.asEntity()

        `when`(api.getAll()).thenReturn(Observable.just(remoteAlbumsOut))
        `when`(dao.getAll()).thenReturn(Single.just(entityAlbumsOut))

        // WHEN -> Execution

        val test = repository.getAll(true).test()

        // THEN -> Verification

        verify(dao).getAll()
        verify(api).getAll()
        verify(dao).insertAll(entityAlbumsOut)

        test.assertResult(domainAlbumsOut)
    }

    @Test
    fun `Get all album with success when Albums not exist in local and refresh from remote`() {
        // GIVEN -> Preparation

        val domainAlbumsOut = listOf(
                MockedObjects.domainFirstAlbum,
                MockedObjects.domainSecondAlbum
        )
        val remoteAlbumsOut = domainAlbumsOut.asRemote()
        val entityAlbumsOut = domainAlbumsOut.asEntity()

        `when`(api.getAll()).thenReturn(Observable.just(remoteAlbumsOut))
        `when`(dao.getAll()).thenReturn(Single.error(MockedObjects.localError))

        // WHEN -> Execution

        val test = repository.getAll(true).test()

        // THEN -> Verification

        verify(dao).getAll()
        verify(api).getAll()
        verify(dao).insertAll(entityAlbumsOut)

        test.assertResult(domainAlbumsOut)
    }

    @Test
    fun `Get all album with success when Albums exist in local and we have an error from remote`() {
        // GIVEN -> Preparation

        val domainAlbumsOut = listOf(
                MockedObjects.domainFirstAlbum,
                MockedObjects.domainSecondAlbum
        )
        val entityAlbumsOut = domainAlbumsOut.asEntity()

        `when`(api.getAll()).thenReturn(Observable.error(MockedObjects.remoteError))
        `when`(dao.getAll()).thenReturn(Single.just(entityAlbumsOut))

        // WHEN -> Execution

        val test = repository.getAll(true).test()

        // THEN -> Verification

        verify(dao).getAll()
        verify(api).getAll()

        test.assertResult(domainAlbumsOut)
    }

    @Test
    fun `Get all album with composite Error  in local and in remote`() {
        // GIVEN -> Preparation

        `when`(api.getAll()).thenReturn(Observable.error(MockedObjects.remoteError))
        `when`(dao.getAll()).thenReturn(Single.error(MockedObjects.localError))

        // WHEN -> Execution

        val test = repository.getAll(true).test()

        // THEN -> Verification

        verify(dao).getAll()
        verify(api).getAll()

        test.assertError { it is CompositeException }
        test.assertNotComplete()
    }

    @Test
    fun `Get album with success when Album exist in local`() {
        // GIVEN -> Preparation

        val albumId = MockedObjects.testAlbumId

        val domainAlbumOut = MockedObjects.domainFirstAlbum
        val entityAlbumOut = domainAlbumOut.asEntity()

        `when`(dao.getAlbum(albumId)).thenReturn(Single.just(entityAlbumOut))

        // WHEN -> Execution

        val test = repository.getAlbum(albumId).test()

        // THEN -> Verification

        verify(dao).getAlbum(albumId)

        test.assertResult(domainAlbumOut)
    }

    @Test
    fun `Get album with success when Album not exist in local and get it from remote`() {
        // GIVEN -> Preparation

        val albumId = MockedObjects.testAlbumId

        val domainAlbumOut = MockedObjects.domainFirstAlbum
        val domainAlbumsOut = listOf(
                MockedObjects.domainFirstAlbum,
                MockedObjects.domainSecondAlbum
        )
        val remoteAlbumsOut = domainAlbumsOut.asRemote()
        val entityAlbumsOut = domainAlbumsOut.asEntity()

        `when`(api.getAll()).thenReturn(Observable.just(remoteAlbumsOut))
        `when`(dao.getAlbum(albumId)).thenReturn(Single.error(MockedObjects.localError))

        // WHEN -> Execution

        val test = repository.getAlbum(albumId).test()

        // THEN -> Verification

        verify(dao).getAlbum(albumId)
        verify(api).getAll()
        verify(dao).insertAll(entityAlbumsOut)

        test.assertResult(domainAlbumOut)
    }

    @Test
    fun `Get album with Error when Album not exist in local and remote`() {
        // GIVEN -> Preparation

        val albumId = MockedObjects.testAlbumIdNotExist

        val domainAlbumsOut = listOf(
                MockedObjects.domainFirstAlbum,
                MockedObjects.domainSecondAlbum
        )
        val remoteAlbumsOut = domainAlbumsOut.asRemote()
        val entityAlbumsOut = domainAlbumsOut.asEntity()

        `when`(api.getAll()).thenReturn(Observable.just(remoteAlbumsOut))
        `when`(dao.getAlbum(albumId)).thenReturn(Single.error(MockedObjects.localError))

        // WHEN -> Execution

        val test = repository.getAlbum(albumId).test()

        // THEN -> Verification

        verify(dao).getAlbum(albumId)
        verify(api).getAll()
        verify(dao).insertAll(entityAlbumsOut)

        test.assertError { it.message == "Album not fount in remote" }
        test.assertNotComplete()
    }

    @Test
    fun `Get album with Error when Album not exist in local and error from remote`() {
        // GIVEN -> Preparation

        val albumId = MockedObjects.testAlbumId

        `when`(api.getAll()).thenReturn(Observable.error(MockedObjects.remoteError))
        `when`(dao.getAlbum(albumId)).thenReturn(Single.error(MockedObjects.localError))

        // WHEN -> Execution

        val test = repository.getAlbum(albumId).test()

        // THEN -> Verification

        verify(dao).getAlbum(albumId)
        verify(api).getAll()

        test.assertError(MockedObjects.remoteError)
        test.assertNotComplete()
    }

}