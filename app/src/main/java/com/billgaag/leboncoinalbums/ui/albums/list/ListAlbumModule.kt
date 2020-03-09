package com.billgaag.leboncoinalbums.ui.albums.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.billgaag.leboncoinalbums.di.module.ViewModelFactory
import com.billgaag.leboncoinalbums.di.module.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ListAlbumModule {

    @Binds
    abstract fun factory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListAlbumViewModel::class)
    abstract fun viewModel(vm: ListAlbumViewModel): ViewModel
}