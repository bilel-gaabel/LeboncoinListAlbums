package com.billgaag.leboncoinalbums.di.module

import com.billgaag.leboncoinalbums.ui.albums.detail.AlbumDetailModule
import com.billgaag.leboncoinalbums.ui.albums.list.ListAlbumFragment
import com.billgaag.leboncoinalbums.ui.albums.list.ListAlbumModule
import com.billgaag.leboncoinalbums.ui.list.AlbumDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilder {

    @ContributesAndroidInjector(modules = [ListAlbumModule::class])
    fun contributeListAlbumFragment(): ListAlbumFragment

    @ContributesAndroidInjector(modules = [AlbumDetailModule::class])
    fun contributeAlbumDetailFragment(): AlbumDetailFragment
}