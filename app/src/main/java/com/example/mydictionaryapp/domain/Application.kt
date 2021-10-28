package com.example.mydictionaryapp.domain

import com.example.mydictionaryapp.domain.di.DaggerApplicationComponent
import com.example.popularlibraries.domain.schedulers.DefaultSchedulers
import com.github.terrakok.cicerone.Cicerone
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.rxjava3.disposables.CompositeDisposable

class Application : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<Application> =
        DaggerApplicationComponent
            .builder()
            .withContext(applicationContext)
            .apply {
                val cicerone = Cicerone.create()
                withNavigatorHolder(cicerone.getNavigatorHolder())
                withRouter(cicerone.router)
                withSchedulers(DefaultSchedulers())
                withCompositeDisposable(CompositeDisposable())
            }
            .build()
}

