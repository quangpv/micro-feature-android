package com.example.home

import com.example.core.module
import com.example.core.then
import com.example.home.features.config.LoadConfigFeature
import com.example.home.features.config.LoadConfigViewModel
import com.example.home.features.login.GotoLoginFeature
import com.example.home.features.login.GotoLoginViewModel
import com.example.home.features.logout.LogoutFeature
import com.example.home.features.logout.LogoutViewModel
import com.example.home.features.preview.PreviewFeature
import com.example.modules.configure.ConfigProxy
import com.example.modules.home.HomeProxy

val homeModules = module {
    factory { get<HomeProxy>() as HomeEmitter }

    factory { LoadConfigViewModel(get()) }
    factory { GotoLoginViewModel(get()) }
    factory { LogoutViewModel(get()) }

    factory {
        val settings = get<ConfigProxy>().settings
        listOfNotNull(
            PreviewFeature(),
            settings.isEnableLogin then GotoLoginFeature(),
            settings.isEnableLogout then LogoutFeature(),
            settings.isEnableLoadConfig then LoadConfigFeature(),
        )
    }
}