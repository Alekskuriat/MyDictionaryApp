package com.example.mydictionaryapp.presenter.DictionaryPresenter


import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryView
import com.example.popularlibraries.domain.schedulers.Schedulers
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DictionaryPresenterImpl<V : DictionaryView>
@Inject constructor(
    private val repo: DictionaryRepository,
    private val router: Router,
    private val schedulers: Schedulers,
    private val disposable: CompositeDisposable
) : DictionaryPresenter<V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        disposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        disposable.add(
            repo
                .getData(word, isOnline)
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .doOnSubscribe {
                    currentView?.showLoading()
                }
                .subscribe(
                    {
                        currentView?.showWords(it)
                    },
                    {
                        currentView?.showError(it)
                    }
                )
        )
    }

}