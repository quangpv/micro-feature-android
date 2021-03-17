package com.example.modules.module

interface ModuleEvent {
    operator fun invoke(action: ModuleAction) {
        onEvent(action)
    }

    fun onEvent(action: ModuleAction)
}