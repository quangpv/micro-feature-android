package com.example.simpleapp.modules.home

import com.example.core.module
import com.example.home.HomeProxyImpl
import com.example.home.homeModules
import com.example.modules.home.HomeProxy

val homeAppModule = module {
    single<HomeProxy> { HomeProxyImpl(HomeCoordinator()) }

    modules(homeModules)
}