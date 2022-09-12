package com.exam.acronym

import android.app.Application
import com.exam.acronym.modules.networkModule
import com.exam.acronym.modules.repositoryModule
import com.exam.acronym.modules.viewModelModule
import org.koin.core.context.startKoin

/**
 * Global [Application] class
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}