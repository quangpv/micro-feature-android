package com.example.home

import com.example.core.module
import com.example.home.features.config.LoadConfigViewModel
import com.example.home.features.config.LoadConfigFeature
import com.example.home.features.login.LoginViewModel
import com.example.home.features.login.LoginFeature
import com.example.home.features.logout.LogoutViewModel
import com.example.home.features.logout.LogoutFeature
import com.example.home.features.preview.PreviewFeature
import com.example.modules.configure.ConfigProxy
import com.example.modules.home.HomeProxy

val homeModules = module {
    factory { get<HomeProxy>() as HomeEmitter }

    factory { LoadConfigViewModel(get()) }
    factory { LoginViewModel(get()) }
    factory { LogoutViewModel(get()) }

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