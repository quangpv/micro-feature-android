package com.example.home

import com.example.core.module
import com.example.home.features.config.LoadConfigContract
import com.example.home.features.config.LoadConfigFeature
import com.example.home.features.login.LoginContract
import com.example.home.features.login.LoginFeature
import com.example.home.features.logout.LogoutContract
import com.example.home.features.logout.LogoutFeature
import com.example.home.features.preview.PreviewFeature
import com.example.modules.configure.ConfigProxy
import com.example.modules.home.HomeProxy

val homeModules = module {
    factory { get<HomeProxy>() as HomeEmitter }

    factory { LoadConfigContract(get()) }
    factory { LoginContract(get()) }
    factory { LogoutContract(get()) }

    factory {
        val settings = get<ConfigProxy>().settings
        listOfNotNull(
            PreviewFeature(),
            if (settings.isEnableLogin) LoginFeature() else null,
            if (settings.isEnableLogout) LogoutFeature() else null,
            if (settings.isEnableLoadConfig) LoadConfigFeature() else null,
        )
    }
}