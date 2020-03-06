package com.billgaag.leboncoin.domain.usecases

import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadAlbumUseCaseTest {

    @Mock
    private lateinit var repository: AlbumsRepository

    @InjectMocks
    private lateinit var usecase: LoadAlbumUseCase

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Get album with success`() {
        // GIVEN -> Preparation

        val albumId = MockedObjects.testAlbumId

        val domainAlbumOut = MockedObjects.domainFirstAlbum

        `when`(repository.getAlbum(albumId)).thenReturn(Observable.just(domainAlbumOut))

        // WHEN -> Execution

        val test = usecase.execute(albumId).test()

        // THEN -> Verification

        verify(repository).getAlbum(albumId)

        test.assertResult(domainAlbumOut)
    }

    @Test
    fun `Get album with Error`() {
        // GIVEN -> Preparation

        val albumId = MockedObjects.testAlbumId

        `when`(repository.getAlbum(albumId)).thenReturn(Observable.error(MockedObjects.error))

        // WHEN -> Execution

        val test = usecase.execute(albumId).test()

        // THEN -> Verification

        verify(repository).getAlbum(albumId)

        test.assertError(MockedObjects.error)
    }
}