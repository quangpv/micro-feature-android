package com.example.simpleapp

import android.app.Application
import com.example.core.ProxyProvider
import com.example.core.module
import com.example.core.provides
import com.example.modules.module.ModuleProxy
import com.example.simpleapp.modules.authAppModule
import com.example.simpleapp.modules.configAppModule
import com.example.simpleapp.modules.homeAppModule

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        provides {
            modules(defaultProxyModules)

            modules(
                configAppModule,
                homeAppModule,
                authAppModule
            )
        }
    }
}

val defaultProxyModules = module {
    single { ProxyProvider[ModuleProxy::class] }
}