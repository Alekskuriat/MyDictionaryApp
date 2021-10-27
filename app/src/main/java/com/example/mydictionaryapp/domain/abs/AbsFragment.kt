package com.example.mydictionaryapp.domain.abs

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.popularlibraries.domain.schedulers.Schedulers
import com.github.terrakok.cicerone.Router
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

abstract class AbsFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId),
    HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var schedulers: Schedulers

    @Inject
    lateinit var disposable: CompositeDisposable


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector


}