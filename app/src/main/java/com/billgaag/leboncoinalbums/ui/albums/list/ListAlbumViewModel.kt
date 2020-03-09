package com.billgaag.leboncoinalbums.ui.albums.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoin.domain.usecases.LoadListAlbumsUseCase
import com.billgaag.leboncoinalbums.utils.Constants
import com.billgaag.leboncoinalbums.utils.Result
import com.billgaag.leboncoinalbums.utils.Result.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Scheduler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListAlbumViewModel
@Inject
constructor(
    private val loadListAlbumsUseCase: LoadListAlbumsUseCase
) : ViewModel() {

    private lateinit var allAlbumList: List<Album>

    val showedAlbumList = MutableLiveData<Result<List<Album>>>()

    fun getListAlbum(scheduler: @NonNull Scheduler = AndroidSchedulers.mainThread()) {
        loadListAlbumsUseCase.execute()
                .observeOn(scheduler)
                .doOnSubscribe { showedAlbumList.postValue(Loading) }
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        allAlbumList = it
                        val listToShow = if (it.size <= Constants.SHOWED_PAGE_SIZE) {
                            it
                        } else {
                            allAlbumList.subList(
                                    0,
                                    Constants.SHOWED_PAGE_SIZE
                            )
                        }

                        showedAlbumList.postValue(
                                Success(listToShow)
                        )
                    }
                }) {
                    showedAlbumList.postValue(Error(it as Exception))
                }
    }

    fun loadNextPageList(fromIndex: Int) {
        if (fromIndex < allAlbumList.size) {
            showedAlbumList.postValue(Loading)
            viewModelScope.launch {
                showedAlbumList.postValue(
                        Success(
                                allAlbumList.subList(
                                        fromIndex,
                                        fromIndex + Constants.SHOWED_PAGE_SIZE
                                )
                        )
                )
            }
        }
    }
}
