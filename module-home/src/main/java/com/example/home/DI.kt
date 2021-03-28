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
        val settings = runCatching { get<ConfigProxy>().settings }.getOrNull()
        listOfNotNull(
            PreviewFeature(),
            (settings?.isEnableLogin ?: true) then GotoLoginFeature(),
            (settings?.isEnableLogout ?: true) then LogoutFeature(),
            (settings?.isEnableLoadConfig ?: true) then LoadConfigFeature(),
        )
    }
}