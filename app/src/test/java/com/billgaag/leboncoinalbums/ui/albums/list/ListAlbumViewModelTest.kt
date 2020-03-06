package com.billgaag.leboncoinalbums.ui.albums.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import com.billgaag.leboncoin.domain.usecases.LoadListAlbumsUseCase
import com.billgaag.leboncoinalbums.ui.albums.MockedObjects
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListAlbumViewModelTest {

    @Mock
    private lateinit var observer: Observer<Result<List<Album>>>

    @Mock
    private lateinit var repository: AlbumsRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()


    val scheduler: Scheduler = Schedulers.trampoline()

    @InjectMocks
    private lateinit var usecase: LoadListAlbumsUseCase

    private lateinit var viewModel: ListAlbumViewModel

    @Before
    fun setUp() {
        viewModel = ListAlbumViewModel(usecase)
        viewModel.showedAlbumList.observeForever(observer);
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Get albums list with success`() {
        // GIVEN -> Preparation
        val listAlbums = listOf(
                MockedObjects.domainFirstAlbum,
                MockedObjects.domainSecondAlbum
        )

        Mockito.`when`(usecase.execute())
                .thenReturn(Observable.just(listAlbums))

        // WHEN -> Execution

        viewModel.getListAlbum(scheduler)

        // THEN -> Verification

        Mockito.verify(observer).onChanged(Result.Loading)
        Mockito.verify(observer).onChanged(Result.Success(listAlbums))
    }

    @Test
    fun `Get albums List with Error`() {
        // GIVEN -> Preparation

        Mockito.`when`(usecase.execute())
                .thenReturn(Observable.error(MockedObjects.error))

        // WHEN -> Execution

        viewModel.getListAlbum(scheduler)

        // THEN -> Verification

        Mockito.verify(observer).onChanged(Result.Loading)
        Mockito.verify(observer).onChanged(Result.Error(MockedObjects.error))
    }

}