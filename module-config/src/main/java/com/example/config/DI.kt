package com.example.config

import com.example.core.module
import com.example.modules.configure.ConfigSettings

val configModules = module {
    single<ConfigRepository> {
        ConfigRepositoryImpl()
    }

    single<ConfigSettings> {
        ConfigSettingsImpl()
    }
}