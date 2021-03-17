package com.example.simpleapp.modules.config

import com.example.config.ConfigProxyImpl
import com.example.config.configModules
import com.example.core.module
import com.example.modules.configure.ConfigProxy

val configAppModule = module {
    single<ConfigProxy> { ConfigProxyImpl(get()) }

    modules(configModules)
}