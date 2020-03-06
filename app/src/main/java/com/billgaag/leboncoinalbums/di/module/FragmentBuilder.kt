package com.billgaag.leboncoinalbums.di.module

import com.billgaag.leboncoinalbums.ui.albums.list.ListAlbumFragment
import com.billgaag.leboncoinalbums.ui.list.AlbumDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilder {
    @ContributesAndroidInjector
    fun contributeListAlbumFragment(): ListAlbumFragment

    @ContributesAndroidInjector
    fun contributeAlbumDetailFragment(): AlbumDetailFragment
}