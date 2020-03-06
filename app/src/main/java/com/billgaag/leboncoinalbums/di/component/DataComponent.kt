package com.billgaag.leboncoinalbums.di.component

import android.content.Context
import com.billgaag.leboncoin.data.source.local.AlbumsDataBase
import com.billgaag.leboncoin.domain.repositories.AlbumsRepository
import com.billgaag.leboncoinalbums.di.module.DataModule
import com.billgaag.leboncoinalbums.di.scope.DataScope
import dagger.Component

@DataScope
@Component(
        modules = [DataModule::class]
)
interface DataComponent {
    companion object {

        private var INSTANCE: DataComponent? = null

        fun create(context: Context): DataComponent {
            if (INSTANCE == null) {
                INSTANCE = DaggerDataComponent.builder()
                        .dataModule(DataModule(context))
                        .build()
            }

            return INSTANCE as DataComponent
        }
    }

    fun albumsRepository(): AlbumsRepository

    fun database(): AlbumsDataBase
}