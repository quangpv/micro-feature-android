package com.example.simpleapp.modules

import com.example.config.ConfigGateway
import com.example.config.configModules
import com.example.core.module
import com.example.modules.configure.ConfigProxy

val configAppModule = module {
    single<ConfigProxy> { ConfigGateway(get()) }

    modules(configModules)
}