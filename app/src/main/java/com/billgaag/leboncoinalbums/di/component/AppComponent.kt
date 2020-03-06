package com.billgaag.leboncoinalbums.di.component

import android.app.Application
import com.billgaag.leboncoinalbums.di.module.ActivityBuilder
import com.billgaag.leboncoinalbums.di.module.AppModule
import com.billgaag.leboncoinalbums.di.module.FragmentBuilder
import com.billgaag.leboncoinalbums.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
        modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class, FragmentBuilder::class],
        dependencies = [DataComponent::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun dataComponent(component: DataComponent): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
}