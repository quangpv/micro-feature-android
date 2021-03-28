package com.example.home

import com.example.modules.home.HomeProxy
import com.example.modules.module.ModuleEvent

class HomeGateway(override val emit: ModuleEvent = ModuleEvent.Empty) : HomeProxy, HomeEmitter {
    override val startFragment: String
        get() = HomeFragment::class.java.name
}