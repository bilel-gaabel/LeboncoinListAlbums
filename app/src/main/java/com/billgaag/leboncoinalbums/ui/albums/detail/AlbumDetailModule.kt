package com.billgaag.leboncoinalbums.ui.albums.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.billgaag.leboncoinalbums.di.module.ViewModelFactory
import com.billgaag.leboncoinalbums.di.module.ViewModelKey
import com.billgaag.leboncoinalbums.ui.list.AlbumDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AlbumDetailModule {

    @Binds
    abstract fun factory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailViewModel::class)
    abstract fun viewModel(vm: AlbumDetailViewModel): ViewModel
}