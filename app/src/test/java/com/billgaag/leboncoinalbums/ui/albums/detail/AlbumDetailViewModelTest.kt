package com.billgaag.leboncoinalbums.ui.albums.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import com.billgaag.leboncoin.domain.usecases.LoadAlbumUseCase
import com.billgaag.leboncoinalbums.ui.albums.MockedObjects
import com.billgaag.leboncoinalbums.ui.list.AlbumDetailViewModel
import com.billgaag.leboncoinalbums.utils.Result
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumDetailViewModelTest {

    @Mock
    private lateinit var observer: Observer<Result<Album>>

    @Mock
    private lateinit var repository: AlbumsRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()


    val scheduler: Scheduler = Schedulers.trampoline()

    @InjectMocks
    private lateinit var usecase: LoadAlbumUseCase

    private lateinit var viewModel: AlbumDetailViewModel

    @Before
    fun setUp() {
        viewModel = AlbumDetailViewModel(usecase)
        viewModel.showedAlbum.observeForever(observer);
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Get album with success`() {
        // GIVEN -> Preparation

        `when`(usecase.execute(MockedObjects.testAlbumId))
                .thenReturn(Observable.just(MockedObjects.domainFirstAlbum))

        // WHEN -> Execution

        viewModel.getAlbum(MockedObjects.testAlbumId, scheduler)

        // THEN -> Verification

        verify(observer).onChanged(Result.Loading)
        verify(observer).onChanged(Result.Success(MockedObjects.domainFirstAlbum))
    }

    @Test
    fun `Get album with Error`() {
        // GIVEN -> Preparation

        `when`(usecase.execute(MockedObjects.testAlbumId))
                .thenReturn(Observable.error(MockedObjects.error))

        // WHEN -> Execution

        viewModel.getAlbum(MockedObjects.testAlbumId, scheduler)

        // THEN -> Verification

        verify(observer).onChanged(Result.Loading)
        verify(observer).onChanged(Result.Error(MockedObjects.error))
    }

}