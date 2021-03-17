package com.example.simpleapp.modules.home

import com.example.home.actions.GotoLoginAction
import com.example.modules.module.ModuleAction
import com.example.modules.module.ModuleEvent
import com.example.simpleapp.navigator

class HomeCoordinator : ModuleEvent {
    companion object {
        const val LOGIN = "com.example.authenticate.login.LoginFragment"
    }

    override fun onEvent(action: ModuleAction) {
        when (action) {
            is GotoLoginAction -> action.fragment.navigator
                .showFragment(LOGIN)
        }
    }
}