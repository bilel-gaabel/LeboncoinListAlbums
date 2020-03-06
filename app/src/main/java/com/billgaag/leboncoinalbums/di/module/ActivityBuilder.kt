package com.billgaag.leboncoinalbums.di.module

import com.billgaag.leboncoinalbums.ui.albums.ListAlbumActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {
    @ContributesAndroidInjector
    fun contributeListAlbumActivity(): ListAlbumActivity
}