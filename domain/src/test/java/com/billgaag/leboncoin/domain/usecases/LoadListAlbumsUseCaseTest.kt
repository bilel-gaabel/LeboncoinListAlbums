package com.billgaag.leboncoin.domain.usecases

import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadListAlbumsUseCaseTest {

    @Mock
    lateinit var repository: AlbumsRepository

    @InjectMocks
    private lateinit var usecase: LoadListAlbumsUseCase

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Get list album with success`() {
        // GIVEN -> Preparation

        val domainAlbumsOut =
                listOf(MockedObjects.domainFirstAlbum, MockedObjects.domainSecondAlbum)

        `when`(repository.getAll(true)).thenReturn(Observable.just(domainAlbumsOut))

        // WHEN -> Execution

        val test = usecase.execute().test()

        // THEN -> Verification

        verify(repository).getAll(true)

        test.assertResult(domainAlbumsOut)
    }

    @Test
    fun `Get list album with error`() {
        // GIVEN -> Preparation

        `when`(repository.getAll(true)).thenReturn(Observable.error(MockedObjects.error))

        // WHEN -> Execution

        val test = usecase.execute().test()

        // THEN -> Verification

        verify(repository).getAll(true)

        test.assertError(MockedObjects.error)
    }
}