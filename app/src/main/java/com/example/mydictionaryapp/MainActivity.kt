package com.example.mydictionaryapp

import android.os.Bundle
import com.example.mydictionaryapp.domain.abs.AbsActivity
import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryFragment
import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryScreen
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AbsActivity(R.layout.activity_main) {

    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: router.newRootScreen(DictionaryScreen().show())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}