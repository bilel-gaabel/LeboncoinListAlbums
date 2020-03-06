package com.billgaag.leboncoinalbums

import com.billgaag.leboncoinalbums.di.component.AppComponent
import com.billgaag.leboncoinalbums.di.component.DaggerAppComponent
import com.billgaag.leboncoinalbums.di.component.DataComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class AlbumApplication : DaggerApplication() {

    val component: AppComponent by lazy {
        applicationInjector() as AppComponent
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val dataComponent = DataComponent.create(this)

        val component = DaggerAppComponent.builder()
                .application(this)
                .dataComponent(dataComponent)
                .build()

        component.inject(this)

        return component
    }
}