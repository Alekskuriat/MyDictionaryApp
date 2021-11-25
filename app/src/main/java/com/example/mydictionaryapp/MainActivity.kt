package com.example.mydictionaryapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mydictionaryapp.view.dictionaryScreen.DictionaryScreen
import com.example.mydictionaryapp.view.historyScreen.HistoryScreen
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import android.os.Build
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator = AppNavigator(this, R.id.container)
    private val cicerone = Cicerone.create()
    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSplashScreen(INTERPOLATOR_DURATION, DecelerateInterpolator(INTERPOLATOR_FACTOR))
        setSupportActionBar(findViewById(R.id.toolbar))
        savedInstanceState ?: router.newRootScreen(DictionaryScreen().show())
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setSplashScreen(duration: Long, interpolator: DecelerateInterpolator) {
        splashScreen.setOnExitAnimationListener { splashView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashView,
                View.TRANSLATION_Y,
                0f,
                -splashView.height.toFloat()
            ).apply {
                this.interpolator = interpolator
                this.duration = duration
                doOnEnd { splashView.remove() }
                start()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_history) {
            router.navigateTo(HistoryScreen().show())
        }
        return true
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    companion object {
        private const val INTERPOLATOR_FACTOR = 2f
        private const val INTERPOLATOR_DURATION = 1000L
    }
}

