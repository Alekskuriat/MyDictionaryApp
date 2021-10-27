package com.example.mydictionaryapp.domain.di

import android.content.Context
import com.example.mydictionaryapp.domain.Application
import com.example.mydictionaryapp.domain.di.modules.ApiModule
import com.example.mydictionaryapp.domain.di.modules.DictionaryModule
import com.example.popularlibraries.domain.schedulers.Schedulers
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApiModule::class,
        DictionaryModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        @BindsInstance
        fun withRouter(router: Router): Builder

        @BindsInstance
        fun withNavigatorHolder(navigatorHolder: NavigatorHolder): Builder

        @BindsInstance
        fun withSchedulers(schedulers: Schedulers): Builder

        @BindsInstance
        fun withCompositeDisposable(disposable: CompositeDisposable): Builder

        fun build(): ApplicationComponent
    }

}