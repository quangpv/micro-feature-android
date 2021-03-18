package com.example.simpleapp

import android.app.Application
import com.example.core.ProxyProvider
import com.example.core.module
import com.example.core.provides
import com.example.modules.module.ModuleProxy
import com.example.simpleapp.modules.authGateway
import com.example.simpleapp.modules.configGateway
import com.example.simpleapp.modules.homeGateway

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        provides {
            modules(defaultProxyModules)

            modules(
                configGateway,
                homeGateway,
                authGateway
            )
        }
    }
}

val defaultProxyModules = module {
    single { ProxyProvider[ModuleProxy::class] }
}