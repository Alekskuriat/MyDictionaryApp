package com.example.mydictionaryapp.domain

import android.app.Application
import com.example.mydictionaryapp.domain.di.koin.KoinModules
import org.koin.core.context.startKoin

class TranslatorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(KoinModules().getModules())
        }
    }
}