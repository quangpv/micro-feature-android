package com.example.home

import com.example.modules.home.HomeProxy
import com.example.modules.module.ModuleEvent

class HomeProxyImpl(override val emit: ModuleEvent) : HomeProxy, HomeEmitter {
    override val startFragment: String
        get() = HomeFragment::class.java.name
}