package com.billgaag.leboncoinalbums.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.billgaag.leboncoin.domain.model.Album
import com.billgaag.leboncoin.domain.usecases.LoadAlbumUseCase
import com.billgaag.leboncoinalbums.utils.Result
import com.billgaag.leboncoinalbums.utils.Result.Loading
import com.billgaag.leboncoinalbums.utils.Result.Success
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class AlbumDetailViewModel
@Inject
constructor(
        private val loadAlbumUseCase: LoadAlbumUseCase
) : ViewModel() {
    val showedAlbum = MutableLiveData<Result<Album>>()

    fun getAlbum(id: Int, scheduler: @NonNull Scheduler = AndroidSchedulers.mainThread()) {
        loadAlbumUseCase.execute(id)
                .observeOn(scheduler)
                .doOnSubscribe { showedAlbum.postValue(Loading) }
                .subscribe({
                    if (it != null) {
                        showedAlbum.postValue(Success(it))
                    } else {
                        showedAlbum.postValue(Result.Error(Exception("Album $id not exist")))
                    }
                }) {
                    showedAlbum.postValue(Result.Error(it as Exception))
                }
    }
}
