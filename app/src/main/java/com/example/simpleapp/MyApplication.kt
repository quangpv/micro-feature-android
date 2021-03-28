package com.example.simpleapp

import android.app.Application
import com.example.core.provides
import com.example.core.proxyOf
import com.example.modules.module.ModuleProxy
import com.example.simpleapp.modules.authGateway
import com.example.simpleapp.modules.configGateway
import com.example.simpleapp.modules.homeGateway

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        provides {
            proxyOf<ModuleProxy>(
                configGateway,
                homeGateway,
                authGateway
            )
        }
    }
}