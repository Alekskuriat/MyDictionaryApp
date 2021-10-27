package com.example.mydictionaryapp.domain.abs

import android.content.Context
import androidx.annotation.LayoutRes
import com.example.popularlibraries.domain.schedulers.Schedulers
import com.github.terrakok.cicerone.Router
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class AbsFragment(
    @LayoutRes contentLayoutId: Int
) : androidx.fragment.app.Fragment(),
    HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var schedulers: Schedulers

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}