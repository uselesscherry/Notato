package com.cherry.notato.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin


class NoteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            fragmentFactory()
            androidContext(this@NoteApp)
            modules(appModule)
        }
    }
}