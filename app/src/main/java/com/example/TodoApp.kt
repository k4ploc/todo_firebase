package com.example

import android.app.Application
import com.example.todoappfirebase.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicia Koin
        startKoin {
            androidContext(this@TodoApp) // Proveer el contexto de la aplicación
            modules(appModule) // Registrar el módulo de red
        }
    }
}
