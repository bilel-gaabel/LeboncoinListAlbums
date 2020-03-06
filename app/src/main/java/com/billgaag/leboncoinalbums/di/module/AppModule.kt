package com.billgaag.leboncoinalbums.di.module

import android.app.Application
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import com.billgaag.leboncoin.domain.usecases.LoadAlbumUseCase
import com.billgaag.leboncoin.domain.usecases.LoadListAlbumsUseCase
import com.billgaag.leboncoinalbums.AlbumApplication
import com.billgaag.leboncoinalbums.di.scope.AppScope
import com.billgaag.leboncoinalbums.di.scope.DataScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule